package me.nabil.maven.plugin.restgen.mojos;

import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.classworlds.realm.ClassRealm;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class工具类
 *
 * @author zhangbi
 */
public class ClassUtils {

    /**
     * class文件后缀
     */
    private static final String CLASS_FILE_SUFFIX = ".class";

    /**
     * package名称分隔符
     */
    private static final String PACKAGE_SEPARATOR = ".";

    /**
     * 从目录下递归所有目录读取所有的class
     *
     * @param classLoader classloader
     * @return Class列表
     * @throws ClassNotFoundException
     * @throws MalformedURLException
     */
    public static List<Class<?>> readAllClassFromDir(ClassLoader classLoader, File root,
                                                     File dir)
            throws ClassNotFoundException, MalformedURLException {


        List<Class<?>> classes = new ArrayList<Class<?>>();

        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                classes.addAll(readAllClassFromDir(classLoader, root, file));

            }
        } else if (dir.isFile()) {
            String name = dir.getName();
            if (dir.isFile() && name.endsWith(CLASS_FILE_SUFFIX)) {
                String rootPath = root.getAbsolutePath() + File.separator;
                String path = dir.getParent().replace(rootPath, "");

                // 解析出class全路径,类似com.baidu.pack.XXXClass
                String type = (path + File.separator + name).replace(File.separator, PACKAGE_SEPARATOR)
                        .replace(CLASS_FILE_SUFFIX, "");

                Class<?> clazz = classLoader.loadClass(type);

                classes.add(clazz);
            }
        }

        return classes;

    }


    /**
     * classloader构建,是将项目中的classpath加到maven的classpath中,使得所有的class在一个classloader
     *
     * @param dependencies
     * @return
     * @throws MalformedURLException
     * @throws MojoFailureException
     */
    public static ClassLoader classLoader(Collection<File> dependencies) throws MalformedURLException,
            MojoFailureException {

        if (!(Thread.currentThread().getContextClassLoader() instanceof ClassRealm)) {
            throw new MojoFailureException("classloader is not instance of ClassRealm");
        }

        ClassRealm classRealm = (ClassRealm) Thread.currentThread().getContextClassLoader();

        Set<URL> urls = new HashSet<URL>();
        for (File dependency : dependencies) {
            urls.add(dependency.toURI().toURL());
            classRealm.addURL(dependency.toURI().toURL());
        }

        return Thread.currentThread().getContextClassLoader();
    }

}
