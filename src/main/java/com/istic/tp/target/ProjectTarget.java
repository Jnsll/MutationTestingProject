package com.istic.tp.target;

import org.apache.maven.shared.invoker.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Project Maven
 */
public class ProjectTarget {
    /**
     *
     */
    private JUnitCore jUnitCore;
    /**
     * path of target project
     */
    private String path;


    public ProjectTarget(String path) {
        this.path = path;
        this.jUnitCore = new JUnitCore();


    }

    /**
     * Launch all test
     */
    public void launchTest(){
        final File folder = new File(this.getPathsrcTest());
        try {

                URL[] urls = new URL[]{  new URL("file://"+path+"/target/classes/"),new URL("file://"+path+"/target/test-classes/") };
            URLClassLoader url = new URLClassLoader(urls);

            recursiveLaunchTest(folder,url);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
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

    public boolean clean(){
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
        option.add("clean");

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
            System.err.println("Clean fail for project "+this.path);
            return false;
        }

        return true;


    }


    private void recursiveLaunchTest(final File folder,final URLClassLoader url) throws ClassNotFoundException {

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                recursiveLaunchTest(fileEntry,url);
            } else {


                String name = fileEntry.toString().replace(path+"/target/test-classes/","")
                        .replaceAll(".class","")
                        .replaceAll("/",".");
                Class simpleClass = url.loadClass(name);

                System.out.println("# " +name);
                Result result = jUnitCore.run(simpleClass);
                System.out.println("## Run Count : "+result.getRunCount());
                System.out.println("## Ignore Count : "+result.getIgnoreCount());

                System.out.println("## Failure Count : "+result.getFailureCount());
                for (Failure f : result.getFailures()){
                    System.out.println("\tname : "+f.getTestHeader());
                    System.out.println("\t "+f.getException());
                    System.out.println("\t "+f.getMessage());
                }
                System.out.println();

            }
        }
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
