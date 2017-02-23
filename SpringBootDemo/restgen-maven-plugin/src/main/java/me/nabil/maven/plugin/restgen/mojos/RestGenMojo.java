package me.nabil.maven.plugin.restgen.mojos;

import me.nabil.maven.plugin.restgen.domain.RequestMappingInfo;
import me.nabil.maven.plugin.restgen.surpport.IOUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * restgen Mojo,解析SpringMvc controller中的URL Pattern信息字典
 * <p>
 * 用于性能统计时Restful URL类似/ads/12,转换回/ads/{ids},按照pattern维度进行聚合
 *
 * @author zhangbi
 */
@Mojo(name = "restgen", aggregator = true, inheritByDefault = false)
@Execute(phase = LifecyclePhase.COMPILE)
public class RestGenMojo extends AbstractMyCiMojo {

    /**
     * URL的前缀,用于添加在结果上
     */
    @Parameter(property = "pathPrefix")
    protected String pathPrefix;

    /**
     * 导出文件的路径
     * <p>
     * maven 输出路径,如写 dir/file.txt,则最终文件生成在${project}/target/dir/file.txt
     */
    @Parameter(property = "outputPath", required = true)
    private String outputPath;

    /**
     * maven插件入口
     *
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        /**
         * 处理依赖,主要是传递依赖等
         */
        resolveArtifacts();

        /**
         * 导出信息
         */
        dumpSpringMvcInfo();
    }

    /**
     * 导出映射到文件中
     */
    private void dumpSpringMvcInfo() throws MojoFailureException {


        // 1. 解析出mapping信息
        Set<RequestMappingInfo> requestMappingInfoList = getRequestMappingInfos();

        // 2. 转为输出格式
        List<String> lines = transform2StringList(requestMappingInfoList);

        // 3. 写入文件
        File file = new File(buildOutDirectory.getAbsolutePath(), outputPath);
        try {
            IOUtils.writeLinesAndClose(file, lines, "UTF-8");
            getLog().info(String.format("dump mapping info to %s finish, size:%s",
                    file.getAbsolutePath(), lines.size()));
        } catch (IOException e) {
            throw new MojoFailureException(e.getMessage(), e);
        }

    }

    /**
     * 转换到输出格式
     *
     * @param requestMappingInfoList RequestMappingInfo列表
     * @return String列表
     */
    private List<String> transform2StringList(Set<RequestMappingInfo> requestMappingInfoList) {
        List<String> lines = new ArrayList<String>();
        for (RequestMappingInfo requestMappingInfo : requestMappingInfoList) {
            String line;
            if (pathPrefix == null || pathPrefix.trim().isEmpty()) {
                line = requestMappingInfo.toOutputString();
            } else {
                line = requestMappingInfo.toOutputString(pathPrefix);
            }

            getLog().debug(line);
            lines.add(line);
        }
        return lines;
    }

    /**
     * 解析出mapping信息
     *
     * @return RequestMappingInfo 列表
     * @throws MojoFailureException
     */
    private Set<RequestMappingInfo> getRequestMappingInfos() throws MojoFailureException {

        ControllerClassParser controllerClassParser = new ControllerClassParser(getLog(), generateDependencyFiles(),
                new File((project.getBuild().getOutputDirectory())));

        Set<RequestMappingInfo> requestMappingInfoList = new TreeSet<RequestMappingInfo>();
        try {

            requestMappingInfoList = controllerClassParser.parse();
        } catch (MalformedURLException e) {
            getLog().error(e.getMessage(), e);
            throw new MojoFailureException(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            getLog().error(e.getMessage(), e);
            throw new MojoFailureException(e.getMessage(), e);
        }
        return requestMappingInfoList;
    }


}
