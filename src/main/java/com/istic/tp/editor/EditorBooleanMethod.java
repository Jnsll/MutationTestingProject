package com.istic.tp.editor;

import com.istic.tp.mutant.Mutant;
import com.istic.tp.target.ProjectTarget;
import javassist.*;

public class EditorBooleanMethod extends AbstractEditor {


    public EditorBooleanMethod(ProjectTarget target) {
        super(target);
    }

    @Override
    protected void createListMutant(final CtMethod method) {
        try {
            String returnType;
            returnType = method.getReturnType().getName();
            if (returnType.equals("boolean") || returnType.equals("Boolean")) {
                this.mutants.add(new Mutant(method,null));
//              System.out.println("MUTANT june: "+method.getName()+":"+"boolean");
            }

        }catch(Throwable exc) {
            System.out.println("Oh, no! Something went wrong.");
            System.out.println(exc.getMessage());
            exc.printStackTrace();
        }
    }

    @Override
    protected void replace(Mutant mutant) {
        try {
            mutant.getCtMethod().setBody("{return true;}");
            this.write(mutant.getCtMethod().getDeclaringClass());
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }


}
