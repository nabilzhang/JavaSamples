package me.nabil.demo.mojos;


import io.swagger.annotations.Api;
import io.swagger.config.FilterFactory;
import io.swagger.core.filter.SpecFilter;
import io.swagger.core.filter.SwaggerSpecFilter;
import io.swagger.models.auth.SecuritySchemeDefinition;
import me.nabil.demo.AbstractDocumentSource;
import me.nabil.demo.GenerateException;
import me.nabil.demo.reader.ClassSwaggerReader;
import me.nabil.demo.reader.SpringMvcApiReader;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author tedleman
 *         01/21/15
 * @author chekong
 * 05/13/2013
 */
public class SpringMavenDocumentSource extends AbstractDocumentSource {
    private final SpecFilter specFilter = new SpecFilter();

    public SpringMavenDocumentSource(ApiSource apiSource, Log log) throws MojoFailureException {
        super(log, apiSource);
    }

    @Override
    public void loadDocuments() throws GenerateException {
        if (apiSource.getSwaggerInternalFilter() != null) {
            try {
                LOG.info("Setting filter configuration: " + apiSource.getSwaggerInternalFilter());
                FilterFactory.setFilter((SwaggerSpecFilter) Class.forName(apiSource.getSwaggerInternalFilter()).newInstance());
            } catch (Exception e) {
                throw new GenerateException("Cannot load: " + apiSource.getSwaggerInternalFilter(), e);
            }
        }

        swagger = resolveApiReader().read(apiSource.getValidClasses(Api.class));
        
        if(apiSource.getSecurityDefinitions() != null) {
            for (SecurityDefinition sd : apiSource.getSecurityDefinitions()) {
                for (Map.Entry<String, SecuritySchemeDefinition> entry : sd.getDefinitions().entrySet()) {
                    swagger.addSecurityDefinition(entry.getKey(), entry.getValue());
                }
            }
            // sort security defs to make output consistent
            Map<String, SecuritySchemeDefinition> defs = swagger.getSecurityDefinitions();
            Map<String, SecuritySchemeDefinition> sortedDefs = new TreeMap<String, SecuritySchemeDefinition>();
            sortedDefs.putAll(defs);
            swagger.setSecurityDefinitions(sortedDefs);
        }

        if (FilterFactory.getFilter() != null) {
            swagger = new SpecFilter().filter(swagger, FilterFactory.getFilter(),
                    new HashMap<String, List<String>>(), new HashMap<String, String>(),
                    new HashMap<String, List<String>>());
        }
    }

    private ClassSwaggerReader resolveApiReader() throws GenerateException {
        String customReaderClassName = apiSource.getSwaggerApiReader();
        if (customReaderClassName == null) {
            SpringMvcApiReader reader = new SpringMvcApiReader(swagger, LOG);
            reader.setTypesToSkip(this.typesToSkip);
            return reader;
        } else {
            return getCustomApiReader(customReaderClassName);
        }
    }
}
