package com.istic.tp;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Throwable {
        JUnitCore jUnitCore = new JUnitCore();

        URL classUrl = new URL("file:///home/aferey/Documents/VV/MockMathSoftware/target/test-classes/");
        URL classUrlsrc = new URL("file:///home/aferey/Documents/VV/MockMathSoftware/target/classes/");
        URL[] classUrls = { classUrl,classUrlsrc };
        URLClassLoader url = new URLClassLoader(classUrls);
        Class simpleClass = url.loadClass("fr.istic.vv.AfeTest");
        Result result = jUnitCore.run(simpleClass);
        for (Failure f : result.getFailures()){

            System.out.println(f.getException());
            System.out.println(f.getMessage());
        }



    }
}
