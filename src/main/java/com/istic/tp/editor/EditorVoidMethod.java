package com.istic.tp.editor;

import com.istic.tp.target.ProjectTarget;
import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.NotFoundException;

public class EditorVoidMethod extends AbstractEditor {
    public EditorVoidMethod(ProjectTarget target) {
        super(target);
    }

    @Override
    protected void replace(CtMethod method) {
        String returnType = "";
        try {
            returnType = method.getReturnType().getName();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
       
        if(returnType.equals("void") || returnType.equals("Void")){
            if(method.getLongName().contains(".main(java.lang.String[])")){ // don't replace main method body
                return;
            }
            try {
                method.setBody("{}");
                System.out.println("MUTANT antoine: "+method.getName()+":"+"void");
                this.write(method.getDeclaringClass()); // on enregistre les modif
                this.target.launchTest(); // on lance les tests
                this.revert(method); // on remet a l'etat initial
            } catch (CannotCompileException e) {
                e.printStackTrace();
            }

        }



    }
}
