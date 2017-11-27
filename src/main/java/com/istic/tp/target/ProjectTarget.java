package com.istic.tp.target;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.apache.maven.shared.invoker.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

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
     * Launch all test
     */
    public void launchTest(Writer writer){
        final File folder = new File(this.getPathsrcTest());
        try {
            URL[] urls = new URL[]{  new URL("file://"+path+"/target/classes/"),new URL("file://"+path+"/target/test-classes/") };
            URLClassLoader url = new URLClassLoader(urls);
            boolean pass;
            pass = launchTest(folder, url, writer, true);
            if (pass) {
                writer.write("### This mutant was not killed ! \n");
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }


    /**
     * build the project without test
     * @return the build success
     */
    public boolean build(){
        InvocationRequest request = new DefaultInvocationRequest();
        File file = new File( this.path+"/pom.xml" );
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

        return true;


    }

    /**
     * Clean the project target (delete target folder)
     * @return the clean success
     */
    public boolean clean() {
        InvocationRequest request = new DefaultInvocationRequest();
        File file = new File( this.path+"/pom.xml" );
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


    private boolean launchTest(final File folder, final URLClassLoader url, Writer writer, boolean pass) throws ClassNotFoundException, IOException {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                pass = pass && launchTest(fileEntry, url, writer, pass);
            } else {
                if (FilenameUtils.getExtension(fileEntry.getPath()).equals("class")) {
                    String name = fileEntry.toString().replace(path+"/target/test-classes/","")
                            .replaceAll(".class","")
                            .replaceAll("/",".");
                    Class simpleClass = url.loadClass(name);

                    Result result = jUnitCore.run(simpleClass);

                    if (result.getFailureCount()!=0) {
                        writer.write("## Test Class : " +name);
                        writer.write("\n ### Mutant killed by the following failure(s) : \n");
                        for (Failure f : result.getFailures()){
                            writer.write("\tname : "+f.getTestHeader());
                            writer.write("\t "+f.getException());
                            writer.write("\t "+f.getMessage());
                        }
                        writer.write("\n");
                        writer.write("\n");
                        pass = false;
                    }
                }
            }
        }
        return pass;
    }

    /**
     * find the method nameMethod in the class nameClass
     * return null if the Class or Method  doesn't exist
     *
     * @param nameClass path.to.package.NameClass
     * @param nameMethod
     * @return return null if the Class or Method  doesn't exist
     */
    public CtMethod getMethod(String nameClass,String nameMethod){
        try {
            CtClass cc = pool.get(nameClass);
            if(cc.getName().equals(nameClass)) {
                for (CtMethod ct : cc.getDeclaredMethods()) {
                    if (ct.getName().equals(nameMethod)) {
                        return ct;
                    }
                }
            }
        } catch (NotFoundException e) {
            return null;
        }

        return null;
    }

    /**
     * return all methods of the project
     * @return
     */
    public Set<CtMethod> getMethods(){
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
        return this.path+"/target/classes/";
    }

    /**
     * Path of test class files
     * @return
     */
    public String getPathsrcTest(){
        return this.path+"/target/test-classes/";
    }

}
