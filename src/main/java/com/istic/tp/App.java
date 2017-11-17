package com.istic.tp;


import com.istic.tp.editor.EditorComparison;
import com.istic.tp.editor.AbstractEditor;
import com.istic.tp.editor.EditorArithOperator;
import com.istic.tp.editor.EditorVoidMethod;
import com.istic.tp.editor.EditorBooleanMethod;
import com.istic.tp.target.ProjectTarget;

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

        if(!projectTarget.build()){
            return;
        }
        //tests Afe
        System.out.println("== Arith ==");
        AbstractEditor arithOperator =new EditorArithOperator(projectTarget);
        arithOperator.scan();
        arithOperator.launch();

        System.out.println("== Void ==");
        AbstractEditor voidMethod =new EditorVoidMethod(projectTarget);
        voidMethod.scan();
        voidMethod.launch();
        //tests June
        System.out.println("== Boolean ==");
        AbstractEditor booleanMethod =new EditorBooleanMethod(projectTarget);
        booleanMethod.scan();
        booleanMethod.launch();

        //test Elo
        System.out.println("== Comparison ==");
        AbstractEditor editorComparison = new EditorComparison(projectTarget);
        editorComparison.scan();
        editorComparison.launch();



    }




}
