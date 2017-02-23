package me.nabil.maven.plugin.restgen.mojos;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.artifact.resolver.filter.ScopeArtifactFilter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.dependency.graph.DependencyGraphBuilder;
import org.apache.maven.shared.dependency.graph.DependencyGraphBuilderException;
import org.apache.maven.shared.dependency.graph.DependencyNode;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Ci mojo abstracts
 *
 * @author zhangbi
 */
abstract class AbstractMyCiMojo extends AbstractMojo {

    /**
     * maven project
     */
    @Parameter(property = "project", defaultValue = "${project}")
    protected MavenProject project;

    /**
     * The dependency tree builder to use.
     */
    @Component(hint = "default")
    private DependencyGraphBuilder dependencyGraphBuilder;

    /**
     * localRepository
     */
    @Parameter(required = true, readonly = true, defaultValue = "${localRepository}")
    private ArtifactRepository localRepository;

    /**
     * target目录
     */
    @Parameter(required = true, readonly = true, defaultValue = "${project.build.directory}")
    protected File buildOutDirectory;

    /**
     * 通过maven-dependency-tree插件解析出所有传递依赖的jar
     *
     * @throws MojoExecutionException
     */
    protected void resolveArtifacts() throws MojoExecutionException {
        getLog().info("old artifacts size:" + project.getDependencyArtifacts().size());

        DependencyNode treeroot = createDependencyTree(project, dependencyGraphBuilder, "compile");
        Set<Artifact> artifacts = getArtifactsFromDependencyNode(treeroot);
        for (Artifact artifact : artifacts) {
            artifact.setFile(new File(localRepository.getBasedir()
                    + File.separator + localRepository.pathOf(artifact)));
        }
        project.setDependencyArtifacts(artifacts);

        getLog().info("new artifacts size:" + project.getDependencyArtifacts().size());

    }

    /**
     * 递归解析maven-dependency-tree构造出的依赖树并转换切Artifact集合
     *
     * @param dependencyNode 依赖树的根节点
     * @return Artifact集合
     */
    private Set<Artifact> getArtifactsFromDependencyNode(DependencyNode dependencyNode) {
        Set<Artifact> result = new HashSet<Artifact>();
        if (dependencyNode == null) {
            return result;
        }
        result.add(dependencyNode.getArtifact());

        if (dependencyNode.getChildren() == null || dependencyNode.getChildren().isEmpty()) {
            return result;
        }

        for (DependencyNode child : dependencyNode.getChildren()) {
            result.add(child.getArtifact());
            result.addAll(getArtifactsFromDependencyNode(child));
        }
        return result;

    }


    /**
     * 通过maven-dependency-tree找出依赖树根节点
     * <p>
     * copied from dependency:tree mojo
     *
     * @param project
     * @param dependencyGraphBuilder 依赖树构造器
     * @param scope                  maven生命周期阶段
     * @return DependencyNode, 依赖树根节点
     * @throws MojoExecutionException
     */
    protected DependencyNode createDependencyTree(MavenProject project, DependencyGraphBuilder dependencyGraphBuilder,
                                                  String scope)
            throws MojoExecutionException {
        ArtifactFilter artifactFilter = createResolvingArtifactFilter(scope);
        try {
            return dependencyGraphBuilder.buildDependencyGraph(project, artifactFilter);
        } catch (DependencyGraphBuilderException exception) {
            throw new MojoExecutionException("Cannot build project dependency tree", exception);
        }

    }

    /**
     * Gets the artifact filter to use when resolving the dependency tree.
     * <p>
     * copied from dependency:tree mojo
     *
     * @return the artifact filter
     */
    private ArtifactFilter createResolvingArtifactFilter(String scope) {
        ArtifactFilter filter;

        // filter scope
        if (scope != null) {
            getLog().debug("+ Resolving dependency tree for scope '" + scope + "'");

            filter = new ScopeArtifactFilter(scope);
        } else {
            filter = null;
        }

        return filter;
    }

    /**
     * 项目工程中编译生成的class,以及所有依赖,必须要在依赖树构建后调用,否则拿不到传递依赖
     *
     * @return 项目依赖的所有classpath的路径File对象
     * @throws MalformedURLException
     */
    protected List<File> generateDependencyFiles() {

        List<File> dependencyFiles = new ArrayList<File>();


        // 1.项目依赖的jar(传递依赖已经解决)
        Set<Artifact> dependencyArtifacts = project.getDependencyArtifacts();
        for (Artifact artifact : dependencyArtifacts) {
            getLog().debug("add dependency path to portalci maven plugin classpath:" + artifact.getFile());
            dependencyFiles.add(artifact.getFile());
        }

        // 2.target/class
        dependencyFiles.add(new File((project.getBuild().getOutputDirectory())));
        return dependencyFiles;
    }


}
