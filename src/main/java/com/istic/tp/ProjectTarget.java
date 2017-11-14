package com.istic.tp;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ProjectTarget {
    /**
     *
     */
    private JUnitCore jUnitCore;
    /**
     * path of target project
     */
    private String path;
    /**
     *
     */
    private URLClassLoader url;

    public ProjectTarget(String path) {
        this.path = path;
        this.jUnitCore = new JUnitCore();
        try {
            URL[] urls = new URL[]{  new URL("file://"+path+"/target/classes/"),new URL("file://"+path+"/target/test-classes/") };
            this.url = new URLClassLoader(urls);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public void launchTest(){
        final File folder = new File(path+"/target/test-classes/");
        try {
            recursiveLaunchTest(folder);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    private void recursiveLaunchTest(final File folder) throws ClassNotFoundException {

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                recursiveLaunchTest(fileEntry);
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

    public String getPathsrc(){
        return this.path+"/target/classes/";
    }

    public String getPathsrcTest(){
        return this.path+"/target/test-classes/";
    }
}
