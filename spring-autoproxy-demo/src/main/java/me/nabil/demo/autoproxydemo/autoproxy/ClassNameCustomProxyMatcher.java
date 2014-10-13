package me.nabil.demo.autoproxydemo.autoproxy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

/**
 * Find intercepter according to bean class and predefine bean class <->
 * intercepter class mapping. Each mapping can contain multiple beans and
 * intercepters split by comma.
 * 
 * @author sunyue05
 * 
 */
public class ClassNameCustomProxyMatcher implements CustomProxyMatcher {

    /**
     * Logger for this class
     */
    private static final Logger LOGGER = Logger
            .getLogger(ClassNameCustomProxyMatcher.class);

    private static Map<String, String> classNamesMapping;

    @SuppressWarnings("rawtypes")
    public Object[] proxy(Class beanClass, String beanName,
            BeanFactory beanFactory) {
        if (classNamesMapping == null || classNamesMapping.isEmpty()) {
            LOGGER.warn("Class name custom proxy matcher closed due to classNames property is null.");
            return null;
        }
        List<Object> interceptors = new LinkedList<Object>();
        Set<Map.Entry<String, String>> entrySet = classNamesMapping.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            if (StringUtils.isEmpty(entry.getValue())) {
                continue;
            }
            String[] beanNameMappings = StringUtils.split(entry.getKey(), ",");
            String[] interceptorBeanNames = StringUtils.split(entry.getValue(),
                    ",");

            List<Object> beans = getInterceptors(beanNameMappings,
                    interceptorBeanNames, beanClass, beanFactory);
            if (beans != null) {
                interceptors.addAll(beans);
            }
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    private List<Object> getInterceptors(String[] beanNameMappings,
            String[] interceptorBeanNames, Class beanClass,
            BeanFactory beanFactory) {
        if (beanNameMappings != null) {
            for (String beanNameMapping : beanNameMappings) {
                if (FactoryBean.class.isAssignableFrom(beanClass)) {
                    if (!beanNameMapping
                            .startsWith(BeanFactory.FACTORY_BEAN_PREFIX)) {
                        continue;
                    }
                    beanNameMapping = beanNameMapping
                            .substring(BeanFactory.FACTORY_BEAN_PREFIX.length());
                }
                if (isMatch(beanClass.getName(), beanNameMapping)) {
                    List<Object> beans = getBeans(interceptorBeanNames,
                            beanFactory);
                    if (CollectionUtils.isEmpty(beans)) {
                        return null;
                    }
                    return beans;
                }
            }
        }
        return null;
    }

    private List<Object> getBeans(String[] beanNames, BeanFactory beanFactory) {
        List<Object> beans = new ArrayList<Object>(beanNames.length);
        for (String beanName : beanNames) {
            Object bean = beanFactory.getBean(beanName);
            beans.add(bean);
        }
        return beans;
    }

    /**
     * Return if the given bean name matches the mapped name.
     * <p>
     * The default implementation checks for "xxx*", "*xxx" and "*xxx*" matches,
     * as well as direct equality. Can be overridden in subclasses.
     * 
     * @param beanName
     *            the bean name to check
     * @param mappedName
     *            the name in the configured list of names
     * @return if the names match
     * @see org.springframework.util.PatternMatchUtils#simpleMatch(String,
     *      String)
     */
    protected boolean isMatch(String beanName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, beanName);
    }

    public void setClassNamesMapping(Map<String, String> classNamesMapping) {
        ClassNameCustomProxyMatcher.classNamesMapping = classNamesMapping;
    }

}
