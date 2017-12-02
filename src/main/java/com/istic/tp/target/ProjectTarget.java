package com.istic.tp.target;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.apache.maven.shared.invoker.*;
import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;
import java.lang.reflect.Method;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * Project Maven
 */
public class ProjectTarget {
    /**
     *
     */
    final private JUnitCore jUnitCore;
    /**
     * path of target project
     */
    final private String path;

    /**
     *ClassPool of target project
     */
    private ClassPool pool;


    public ProjectTarget(String path) {
        if(!path.substring(path.length()-1,path.length()).equals("/")){
            path = path.concat("/");
        }
        System.out.println(path);
        this.path = path;
        this.jUnitCore = new JUnitCore();
        this.pool = ClassPool.getDefault();
        //TODO : remove the classPath of the source Project
        //pool.removeClassPath();
        URL[] urls = new URL[0];

        try {
            urls = new URL[]{ new URL("file://"+this.getPathsrc()) };
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < urls.length; i++) {
            try {
                pool.insertClassPath(urls[i].getFile());
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * build the project without test
     * @return the build success
     */
    public boolean build() {

        InvocationRequest request = new DefaultInvocationRequest();
        File file = new File( this.path+"pom.xml" );
        if(!file.exists()){
            System.err.println("the pom file : "+file.getPath()+" doesn't exist in "+this.path);
            return false;
        }
        if(!this.clean()){
            return false;
        }
        request.setPomFile(file);
        request.setOutputHandler(line -> { // cache la sortie standard

        });

        List<String> option = new ArrayList<>();
        option.add("package");
        option.add("-DskipTests");
        request.setGoals( option );
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File("/usr"));

        try
        {
            invoker.execute( request );
        }
        catch (MavenInvocationException e)
        {
            e.printStackTrace();
            System.err.println("Build fail for project "+this.path);
            return false;
        }
        Set<String> dependency = this.listDependency();


        for(int i = 0;i<dependency.size();i++){
            try {
                this.pool.insertClassPath((String) dependency.toArray()[i]);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }

        return true;


    }

    /**
     * list the dependencie of pom.xml target project
     * @return
     */
    public Set<String> listDependency(){
        String mavenLocal = this.getLocalRepoMvn();
        InvocationRequest request = new DefaultInvocationRequest();
        File file = new File( this.path+"pom.xml" );

        Set<String> dependency = new HashSet<>();
        request.setPomFile(file);
        request.setOutputHandler(line -> {
            // for each line
            if(line.endsWith(":compile")) {

                String infoMvn = (line
                        .replaceAll("\\[INFO\\]", "")
                        .replaceAll("\\[WARNING\\]", "")
                        .replaceAll(" ","")
                        .replaceAll(":compile","")
                );

                String[] split = infoMvn.split(":");
                String path = "";
                for(String s : split[0].split("\\.")){
                    path = path+"/"+s;
                }

                dependency.add(mavenLocal + path + "/" + split[1] + "/" + split[3] + "/" + split[1] + "-" + split[3] + "." + split[2]);


            }


        });

        List<String> option = new ArrayList<>();
        option.add("dependency:list");

        request.setGoals( option );
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File("/usr"));


        try
        {
            invoker.execute( request );
        }
        catch (MavenInvocationException e)
        {
            e.printStackTrace();


        }
        return dependency;


    }
    /**
     * Clean the project target (delete target folder)
     * @return the clean success
     */
    public boolean clean() {
        InvocationRequest request = new DefaultInvocationRequest();
        File file = new File( this.path+"pom.xml" );
        if(!file.exists()){
            System.err.println("the pom file : "+file.getPath()+" doesn't exist in "+this.path);
            return false;
        }

        request.setPomFile(file);
        request.setOutputHandler(line -> { // cache la sortie standard

        });

        List<String> option = new ArrayList<>();
        option.add("clean");

        request.setGoals( option );
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File("/usr"));

        try {
            invoker.execute( request );
        }
        catch (MavenInvocationException e) {
            e.printStackTrace();
            System.err.println("Clean fail for project "+this.path);
            return false;
        }

        return true;
    }

    /**
     *
     * get the local mvn repo of user
     * @return
     */
    private String getLocalRepoMvn(){
        InvocationRequest request = new DefaultInvocationRequest();
        final String[] repo = {null};
        File file = new File( this.path+"pom.xml" );
        if(!file.exists()) {
            System.err.println("the pom file : " + file.getPath() + " doesn't exist in " + this.path);
            return null;
        }

        request.setPomFile(file);
        request.setOutputHandler(line -> {
            if(!line.startsWith("[INFO]") && !line.startsWith("[WARNING]")){
                repo[0] = line;
            }

        });

        List<String> option = new ArrayList<>();
        option.add("help:evaluate");
        option.add("-Dexpression=settings.localRepository");


        request.setGoals( option );
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File("/usr"));

        try
        {
            invoker.execute( request );
        }
        catch (MavenInvocationException e)
        {
            e.printStackTrace();


        }

        return repo[0];
    }


    /**
     * launch test via maven
     * @return the result of test
     */
    public String launchTest(){

        InvocationRequest request = new DefaultInvocationRequest();
        File file = new File( this.path+"pom.xml" );
        if(!file.exists()){
            System.err.println("the pom file : "+file.getPath()+" doesn't exist in "+this.path);

        }

        // set the parser of output
        MvnTestOutputHandler myInvocationOutputHandler = new MvnTestOutputHandler();
        request.setOutputHandler(myInvocationOutputHandler);

        // configure mvn command
        List<String> option = new ArrayList<>();
        option.add("surefire:test");
        request.setPomFile(file);
        request.setGoals( option );
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File("/usr"));

        try {
            invoker.execute( request );
        }
        catch (MavenInvocationException e) {
            e.printStackTrace();
            System.err.println("Launch Test MVN fail for project "+this.path);

        }


        // return no fail test
        if(myInvocationOutputHandler.getListError().size() <= 2){
            return "The mutant was not killed !\n\n";
        }
        // return fail test
        String result ="The mutant was killed by the following test(s):\n \n";
        for(String s : myInvocationOutputHandler.getListError()){
            result=result+s+"\n";
        }


        return result;
    }



    /**
     * find the method nameMethod in the class nameClass
     * return null if the Class or Method  doesn't exist
     *
     * @param nameClass path.to.package.NameClass
     * @param nameMethod nameMethod
     * @return return null if the Class or Method  doesn't exist
     */
    public CtMethod getMethod(String nameClass,String nameMethod) {
        try {
            CtClass cc = pool.get(nameClass);
            return cc.getDeclaredMethod(nameMethod);

        } catch (NotFoundException e) {

            return null;
        }


    }

    /**
     * return all methods of the project
     * @return
     */
    public Set<CtMethod> getMethods() {
        final File folder = new File(this.getPathsrc());
        Set<String> classes = this.findAllClasses(folder);
        Set<CtMethod> methods = new HashSet<>();

        for(String nameClass : classes) {
            try {
                CtClass cc = pool.get(nameClass);
                for (CtMethod ct : cc.getDeclaredMethods()) {

                    if (!ct.isEmpty()) { // to avoid add interface methods
                        methods.add(ct);
                    }
                }
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        return methods;
    }


    /**
     *
     * @param folder the folder where we find Class
     * @return a set of name Class
     * @throws ClassNotFoundException
     */
    private Set<String> findAllClasses(final File folder) {
        Set<String> classes = new HashSet<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                classes.addAll(findAllClasses(fileEntry));
            } else {
                if (FilenameUtils.getExtension(fileEntry.getPath()).equals("class")) {
                    String name = fileEntry.toString()
                            .replace(this.getPathsrc(),"")
                            .replaceAll("\\.class","")
                            .replaceAll("/",".");

                    classes.add(name);
                }
            }
        }
        return classes;
    }

    /**
     * Path of class files
     * @return
     */
    public String getPathsrc(){
        return this.path+"target/classes/";
    }

    /**
     * Path of test class files
     * @return
     */
    public String getPathsrcTest(){
        return this.path+"target/test-classes/";
    }

}
