package com.istic.tp.operone;

import com.istic.tp.ProjectTarget;
import com.istic.tp.operatorexpr.AbstractEditor;
import javassist.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class EditorBooleanMethod extends AbstractEditor {


    public EditorBooleanMethod(ProjectTarget target) {
        super(target);
    }

    @Override
    protected void replace(final CtMethod method) {
        try {
            String returnType;
            returnType = method.getReturnType().getName();
            //System.out.println(cm.getLongName() + "all methods");
            if (returnType.equals("boolean") || returnType.equals("Boolean")) {
                //System.out.println(cm.getLongName() + "boolean method");

                method.setBody("{return true;}");
                System.out.println("MUTANT june: "+method.getName()+":"+"boolean");
                this.write(method.getDeclaringClass()); // on enregistre les modif
                this.target.launchTest(); // on lance les tests
                this.revert(method); // on remet a l'etat initial
            }
            this.target.launchTest();
            this.revert(cm);
        }catch(Throwable exc) {
            System.out.println("Oh, no! Something went wrong.");
            System.out.println(exc.getMessage());
            exc.printStackTrace();
        }
    }


//    public void editor(URL[] urls) throws NotFoundException, CannotCompileException, IOException {
//        try {
//            ClassPool pool = ClassPool.getDefault();
//            for (int i = 0; i < urls.length; i++) {
//                try {
//
//                    pool.insertClassPath(urls[i].getFile());
//                } catch (NotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//            CtClass cc = pool.get("fr.istic.vv.june.OperatorOne");
//            CtMethod cm = cc.getDeclaredMethod("doesAEqualTen");
//            System.out.println(cm.getLongName());
//            cm.setBody("{return true;}");
//            cc.writeFile(path + "/target/classes/");
//        }
//        catch(Throwable exc) {
//            System.out.println("Oh, no! Something went wrong.");
//            System.out.println(exc.getMessage());
//            exc.printStackTrace();
//        }
//    }
}
