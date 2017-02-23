package me.nabil.maven.plugin.restgen.mojos;

import me.nabil.maven.plugin.restgen.domain.RequestMappingInfo;
import me.nabil.maven.plugin.restgen.exception.HttpMethodEmptyException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Controller上的Request Mapping信息的解析
 * <p/>
 * 通过加载classloader load该项目编译完成的所有class来实现
 *
 * @author zhangbi
 */
public class ControllerClassParser {

    /**
     * maven log 对象
     */
    private Log log;

    /**
     * 依赖的所有jar/class等的路径
     */
    private List<File> dependencyFiles;

    /**
     * 项目target/class所在路径
     */
    private File root;

    /**
     * 构造方法
     *
     * @param log             maven log 对象
     * @param dependencyFiles 依赖的所有jar/class等的路径
     * @param root            项目target/class所在路径
     */
    ControllerClassParser(Log log, List<File> dependencyFiles,
                          File root) {
        this.log = log;
        this.dependencyFiles = dependencyFiles;
        this.root = root;
    }

    /**
     * 通过classloader装载所有Controller类
     *
     * @return class集合
     * @throws MalformedURLException
     * @throws ClassNotFoundException
     * @throws MojoFailureException
     */
    public Collection<Class<?>> loadControllerClassesFromClassloader() throws MalformedURLException,
            ClassNotFoundException, MojoFailureException {

        // 这里classloader是maven的classloader,把所有工程依赖的jar和编译出来的文件都放到maven的classloader中
        // 如果这里classloader不一致,很可能导致下面class获取不到/annotation检测有误等一系列问题
        ClassLoader targetClassLoader = ClassUtils.classLoader(dependencyFiles);

        List<Class<?>> allClasses = ClassUtils.readAllClassFromDir(targetClassLoader,
                root, root);

        List<Class<?>> controllerClasses = new ArrayList<Class<?>>();
        for (Class<?> clz : allClasses) {
            if (clz.isAnnotationPresent(Controller.class) || clz.isAnnotationPresent(RestController.class)) {
                controllerClasses.add(clz);
            }
        }

        return controllerClasses;
    }

    /**
     * 解析controller中的RequestMappingInfo信息
     *
     * @return RequestMappingInfo的TreeSet信息
     * @throws MalformedURLException
     * @throws ClassNotFoundException
     */
    public Set<RequestMappingInfo> parse() throws MalformedURLException, ClassNotFoundException,
            MojoFailureException {

        Set<RequestMappingInfo> requestMappingInfos = new TreeSet<RequestMappingInfo>();

        Collection<Class<?>> controllerClasses = loadControllerClassesFromClassloader();
        for (Class<?> clz : controllerClasses) {
            requestMappingInfos.addAll(parseOneClass(clz));
        }

        return requestMappingInfos;
    }

    /**
     * 解析某一个class的mapping信息
     *
     * @param clz class
     * @return RequestMappingInfo TreeSet
     */
    private Set<RequestMappingInfo> parseOneClass(Class<?> clz) throws MojoFailureException {
        Set<RequestMappingInfo> requestMappingInfos = new TreeSet<RequestMappingInfo>();

        // class上的映射配置
        RequestMapping classRequestMapping = clz.getAnnotation(RequestMapping.class);

        Set<RequestMappingInfo> classRequestMappingSet = createRequestMappingInfosForClass(classRequestMapping);


        // method上的映射配置
        for (Method method : clz.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(RequestMapping.class)) {
                continue;
            }

            RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
            Set<RequestMappingInfo> methodRequestMappingSet = new TreeSet<RequestMappingInfo>();

            try {
                methodRequestMappingSet = createRequestMappingInfosForMethod(methodRequestMapping);
            } catch (HttpMethodEmptyException hmeex) {
                // 如果发现有RequestMapping的http method没有配置,会打日志建议配置
                log.error(String.format(String.format("RequestMapping parse error,class:%s,"
                        + "method:%s,errormsg:%s", clz.getName(), method.getName(), hmeex.getMessage())));
            } catch (Exception ex) {
                // 其他异常则直接抛出Build Failure
                throw new MojoFailureException(String.format("RequestMapping parse error,class:%s,"
                        + "method:%s,errormsg:%s", clz.getName(), method.getName(), ex.getMessage()), ex);
            }

            // 将class上的mapping信息和method上的mapping信息进行merge
            if (classRequestMappingSet == null || classRequestMappingSet.isEmpty()) {
                requestMappingInfos.addAll(methodRequestMappingSet);
            } else {
                Set<RequestMappingInfo> mergeRequestMappingSet = merge(classRequestMappingSet,
                        methodRequestMappingSet);
                requestMappingInfos.addAll(new ArrayList<RequestMappingInfo>(mergeRequestMappingSet));
            }

        }


        return requestMappingInfos;
    }

    /**
     * 进行merge
     *
     * @param classRequestMappingSet  class上的mapping信息
     * @param methodRequestMappingSet method上的mapping信息
     * @return RequestMappingInfo TreeSet
     */
    private Set<RequestMappingInfo> merge(Set<RequestMappingInfo> classRequestMappingSet,
                                          Set<RequestMappingInfo> methodRequestMappingSet) {
        Set<RequestMappingInfo> mergedRequestMappingSet = new TreeSet<RequestMappingInfo>();

        // 1. method 取个并集
        Set<String> httpMethods = new HashSet<String>(); // 支持的所有method
        for (RequestMappingInfo mappingInfo : classRequestMappingSet) {
            if (!RequestMappingInfo.EMPTY_METHOD.equals(mappingInfo.getMethod())) {
                httpMethods.add(mappingInfo.getMethod());
            }
        }
        for (RequestMappingInfo mappingInfo : methodRequestMappingSet) {
            httpMethods.add(mappingInfo.getMethod());
        }


        // 2. 拼接
        for (String httpMethod : httpMethods) {
            for (RequestMappingInfo classMappingInfo : classRequestMappingSet) {
                for (RequestMappingInfo methodMappingInfo : methodRequestMappingSet) {
                    RequestMappingInfo requestMappingInfo = new RequestMappingInfo();
                    requestMappingInfo.setMethod(httpMethod);

                    String urlPattern = classMappingInfo.getUrlPattern()
                            + "/" + methodMappingInfo.getUrlPattern();
                    // 拼接的时候可能有多个分隔符,需要去掉重复,并且对于不是/的path去掉尾部的/
                    urlPattern = urlPattern.replace("///", "/").replace("//", "/");
                    if (!"/".equals(urlPattern) && urlPattern.endsWith("/")) {
                        urlPattern = urlPattern.substring(0, urlPattern.lastIndexOf("/"));
                    }
                    requestMappingInfo.setUrlPattern(urlPattern);

                    mergedRequestMappingSet.add(requestMappingInfo);
                }
            }
        }

        return mergedRequestMappingSet;
    }

    /**
     * class上的映射解析
     *
     * @param classRequestMapping RequestMapping对象
     * @return RequestMappingInfo TreeSet
     */
    private Set<RequestMappingInfo> createRequestMappingInfosForClass(RequestMapping classRequestMapping) {
        return createRequestMappingInfos(classRequestMapping);
    }

    /**
     * method上的映射解析
     *
     * @param methodRequestMapping RequestMapping对象
     * @return RequestMappingInfo TreeSet
     */
    private Set<RequestMappingInfo> createRequestMappingInfosForMethod(RequestMapping methodRequestMapping) {

        // 这个基本不会发生
        if (methodRequestMapping == null) {
            throw new RuntimeException("method RequestMapping is null,please configure it");
        }

        if (methodRequestMapping.method() == null || methodRequestMapping.method().length == 0) {
            throw new HttpMethodEmptyException("method RequestMapping.method() is null, please configure it");
        }

        return createRequestMappingInfos(methodRequestMapping);
    }

    /**
     * 构造RequestMappingInfo
     *
     * @param requestMapping Spring的RequestMapping配置
     * @return RequestMappingInfo TreeSet
     */
    private Set<RequestMappingInfo> createRequestMappingInfos(RequestMapping requestMapping) {
        Set<RequestMappingInfo> classRequestMappingSet = new TreeSet<RequestMappingInfo>();

        if (requestMapping == null) {
            return classRequestMappingSet;
        }

        RequestMethod[] requestMethods = requestMapping.method();

        if (requestMethods == null || requestMethods.length == 0) {
            for (String path : requestMapping.value()) {
                classRequestMappingSet.add(new RequestMappingInfo(RequestMappingInfo.EMPTY_METHOD, path));
            }

        } else {
            for (RequestMethod requestMethod : requestMethods) {
                for (String path : requestMapping.value()) {
                    classRequestMappingSet.add(new RequestMappingInfo(requestMethod.name(), path));
                }
            }
        }

        return classRequestMappingSet;
    }


}
