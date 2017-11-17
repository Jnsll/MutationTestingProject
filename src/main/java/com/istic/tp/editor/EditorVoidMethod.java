package com.istic.tp.editor;

import com.istic.tp.mutant.Mutant;
import com.istic.tp.target.ProjectTarget;
import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.NotFoundException;

public class EditorVoidMethod extends AbstractEditor {
    public EditorVoidMethod(ProjectTarget target) {
        super(target);
    }

    @Override
    protected void createListMutant(CtMethod method) {
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
            this.mutants.add(new Mutant(method,null));
//          System.out.println("MUTANT antoine: "+method.getName()+":"+"void");
        }
    }

    @Override
    protected void replace(Mutant mutant) {
        try {
            mutant.getCtMethod().setBody("{}");
            this.write(mutant.getCtMethod().getDeclaringClass());
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }
}
