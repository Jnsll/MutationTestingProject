package com.istic.tp;


import com.istic.tp.comparisonexpr.EditorComparisation;
import com.istic.tp.operatorexpr.AbstractEditor;
import com.istic.tp.operatorexpr.EditorArthOperator;
import com.istic.tp.operatorexpr.EditorVoidMethod;
import com.istic.tp.operone.EditorJ;

import java.io.File;
import java.net.URL;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Throwable {
        if(args.length != 1){
            System.err.println("need a location of target project");
            return;
        }

        File file = new File(args[0]);

        if(!file.exists()){
            System.err.println("the directory doesn't exist");
           return;
        }

        if(!file.isDirectory()){
            System.err.println("need a directory, not a file");
           return;
        }


        URL[] urls = {  new URL("file://"+args[0]+"/target/test-classes/"),
                        new URL("file://"+args[0]+"/target/classes/") };

        ProjectTarget projectTarget = new ProjectTarget(args[0]);


        //tests Afe
        AbstractEditor afe =new EditorArthOperator(args[0]);
        afe.editor();

        AbstractEditor afe2 =new EditorVoidMethod(args[0]);
        afe2.editor();

        //tests June
        EditorJ june =new EditorJ(args[0]);
        june.editor();

        //test Elo
        EditorComparisation editorComparisation = new EditorComparisation(args[0]);
        editorComparisation.editor(urls);

        projectTarget.launchTest();




    }




}
