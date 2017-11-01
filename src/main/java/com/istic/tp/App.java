package com.istic.tp;


import com.istic.tp.operatorexpr.AbstractEditor;
import com.istic.tp.operatorexpr.EditorOperator;
import com.istic.tp.comparisonexpr.EditorComparisation;
import com.istic.tp.operone.EditorJ;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Throwable {
        // Edit

        URL classUrl = new URL("file://"+args[0]+"/target/test-classes/");
        URL classUrlsrc = new URL("file://"+args[0]+"/target/classes/");
        URL[] urls = { classUrl,classUrlsrc };

        //tests Afe
        AbstractEditor afe =new EditorOperator(args[0],urls);
        afe.editor();

        //tests June
        EditorJ june =new EditorJ(args[0]);
        june.editor(urls);

        //test Elo
        EditorComparisation editorComparisation = new EditorComparisation(args[0]);
        editorComparisation.editor(urls);


        // Test project target
        JUnitCore jUnitCore = new JUnitCore();
        URLClassLoader url = new URLClassLoader(urls);
        final File folder = new File(args[0]+"/target/test-classes/");
        listFilesForFolder(folder,jUnitCore,url,args[0]);


    }

    public static void listFilesForFolder(final File folder,final JUnitCore jUnitCore, final URLClassLoader urlclass,String args ) throws ClassNotFoundException {

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry,jUnitCore,urlclass,args);
            } else {


                String name = fileEntry.toString().replace(args+"/target/test-classes/","")
                        .replaceAll(".class","")
                        .replaceAll("/",".");
                System.out.println("\n# " +name);
                Class simpleClass = urlclass.loadClass(name);
                Result result = jUnitCore.run(simpleClass);
                System.out.println("## Run Count : "+result.getRunCount());
                System.out.println("## Ignore Count : "+result.getIgnoreCount());

                System.out.println("## Failure Count : "+result.getFailureCount());
                for (Failure f : result.getFailures()){
                    System.out.println("\t "+f.getTestHeader());
                    System.out.println("\t "+f.getException());
                    System.out.println("\t "+f.getMessage()+"\n");
                }

            }
        }
    }


}
