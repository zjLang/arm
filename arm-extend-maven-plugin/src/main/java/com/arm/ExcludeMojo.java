package com.arm;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;
import org.apache.maven.shared.transfer.repository.RepositoryManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 修改pom文件
 * defaultPhase : 如果用户没有在POM中明确设置此Mojo绑定到的phase，那么绑定一个MojoExecution到那个phase
 * requiresDirectInvocation : 提示此Mojo需要被直接调用（而非绑定到生命周期阶段）
 * <p>
 * 编译之前执行排除，
 * <p>
 * <p>
 * 修改条件： 如果pom文件有排除则跳过，无则添加排除文件
 */
@Mojo(name = "exclude", defaultPhase = LifecyclePhase.PREPARE_PACKAGE, threadSafe = true
        //requiresDependencyCollection = ResolutionScope.COMPILE_PLUS_RUNTIME
)
public class ExcludeMojo extends AbstractMojo {

    // project maven 定义的变量值
    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession session;


    @Parameter(defaultValue = "${mojoExecution}", readonly = true)
    private MojoExecution mojo;


    @Parameter(defaultValue = "${plugin}", readonly = true)
    private PluginDescriptor plugin;

    @Parameter(defaultValue = "${settings}", readonly = true)
    private Settings settings;

    // test 自定义的变量值，如下命令：mvn clean arm-extend:exclude -s=D:\repository\settings.xml -Dtest=aaa
    /*@Parameter(required = false, defaultValue = "${test}")
    private String test;*/

    /**
     * 传入参数，配置artifactId的值 ，eg: arm-encry,arm-java
     */
    @Parameter(required = false, defaultValue = "${exclude}")
    private String exclude;

    /**
     * 如果加了强制, 默认为true
     */
    @Parameter(required = false, defaultValue = "${force}")
    private boolean force = true;

    @Component
    private RepositoryManager repositoryManager;

    public void execute() throws MojoExecutionException, MojoFailureException {
        //extracted();
        getLog().info("maven ExcludeMojo execute start ..." + exclude);
        File pomFile = project.getFile(); // 获取项目的pom.xml文件。
        String packaging = project.getPackaging();
        // 如果包是war包才有这个功能，只能排除直接依赖 packaging.trim().equals("war") 去掉这个判断，springboot打包是个jar。
        if (StringUtils.isNotBlank(exclude)) {
            // 执行排除依赖开始开始
            getLog().info("maven ExcludeMojo execute exclude dependency start ..." + exclude);
            try {
                exclude(pomFile, new HashSet(Arrays.asList(exclude.split(","))));
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
                getLog().info("maven ExcludeMojo execute exclude dependency error ..." + exclude);
                throw new MojoExecutionException("\"maven ExcludeMojo execute exclude dependency error...", e);
            } catch (IOException e) {
                e.printStackTrace();
                getLog().info("maven ExcludeMojo execute exclude dependency error ..." + exclude);
                throw new MojoExecutionException("\"maven ExcludeMojo execute exclude dependency error...", e);
            } catch (SAXException e) {
                e.printStackTrace();
                getLog().info("maven ExcludeMojo execute exclude dependency error ..." + exclude);
                throw new MojoExecutionException("\"maven ExcludeMojo execute exclude dependency error...", e);
            }
            getLog().info("maven ExcludeMojo execute exclude dependency success ..." + exclude);
        }
    }

    private void exclude(File file, Set excludeDependency) throws ParserConfigurationException, IOException, SAXException, MojoExecutionException {
        Document document = getDocument(file);

        NodeList dependencies = document.getElementsByTagName("dependency");
        int dependenciesLength = dependencies.getLength();
        Set<String> scope = new HashSet<String>();
        for (int i = 0; i < dependenciesLength; i++) {
            // dependency子节点
            Node dependency = dependencies.item(i);
            NodeList childNodes = dependency.getChildNodes();
            int childNodesLength = childNodes.getLength();

            // 如果该依赖存在 是需要排除的节点 , 如果节点已经排除了，则不需要排除
            Node item;
            int index = -1;
            boolean needAddScope = false; // 需要添加排除
            String tempArtifactId = null;
            for (int i1 = 0; i1 < childNodesLength; i1++) {
                item = childNodes.item(i1);
                if (item.getNodeName().equals("artifactId") && item.getTextContent() != null) {
                    tempArtifactId = item.getTextContent();
                    if (excludeDependency.contains(item.getTextContent())) {
                        index = i;
                        scope.add(tempArtifactId); // 添加排除的artifactId
                        needAddScope = true;
                    }
                }
                if (item.getNodeName().equals("scope")) {
                    // 强制更新
                    if (force) {
                        item.setTextContent("provided");
                    }
                    needAddScope = false;
                    scope.add(tempArtifactId); // 添加排除的artifactId
                    break; // 判断有排除立即跳出。
                }
            }
            if (needAddScope) {
                Element element = document.createElement("scope");
                element.setTextContent("provided");
                dependencies.item(index).appendChild(element);
            }
        }
        if (!CollectionUtils.containsAll(scope, excludeDependency)) {
            getLog().info("maven ExcludeMojo execute exclude dependency error : Incomplete replacement..." + exclude);
            throw new MojoExecutionException("maven ExcludeMojo execute exclude dependency error : Incomplete replacement");
        }
        out(file, document);
    }

    /**
     * 废弃方法，使用修改Artifacts方式无法完成目标结果。
     */
    @Deprecated
    private void extracted() {
        File file = project.getFile(); // 获取项目的pom.xml文件。
        String packaging = project.getPackaging();
        // requiresDependencyCollection = ResolutionScope.COMPILE_PLUS_RUNTIME @mojo需要指定
        Set<Artifact> artifacts = project.getArtifacts();
        getLog().info("ExcludeMojo start..." + artifacts.size());

        if (StringUtils.isBlank(exclude)) {
            exclude = "arm-encry,arm-java";
        }
        if (packaging.trim().equals("war") && StringUtils.isNotBlank(exclude)) {
            getLog().info("ExcludeMojo execute exclude dependency start ..." + exclude);
            String[] split = exclude.split(",");
            for (String str : split) {
                Iterator<Artifact> iterator = artifacts.iterator();
                while (iterator.hasNext()) {
                    if (str.equals(iterator.next().getArtifactId())) {
                        getLog().info("ExcludeMojo execute exclude dependency " + str);
                        iterator.remove();
                    }
                }
            }
        }
        // 第一步，取出标识和修改
        //getLog().info("ExcludeMojo start...");
        //getLog().info("ExcludeMojo start..." + project.toString());
        getLog().info("ExcludeMojo execute exclude end..." + artifacts.size());
        getLog().info("ExcludeMojo start..." + artifacts.toString());
    }

    public static void out(File filePath, Document doc) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(filePath);
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }


    private static Document getDocument(File filePath) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = docFactory.newDocumentBuilder();
        Document document = builder.parse(filePath);
        return document;
    }

    public static void main(String[] args) throws MojoExecutionException, ParserConfigurationException, IOException, SAXException {
        //exclude(new File("F:\\myGIT\\private\\arm\\arm-war\\pom.xml"), new HashSet(Arrays.asList(("arm-encry,arm-java").split(","))));
    }

}
