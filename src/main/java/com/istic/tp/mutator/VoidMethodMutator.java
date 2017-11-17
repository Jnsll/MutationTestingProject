package com.istic.tp.mutator;

import com.istic.tp.mutant.Mutant;
import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class VoidMethodMutator extends Mutator {
//    public VoidMethodMutator(ProjectTarget target) {
//        super(target);
//    }

    @Override
    public List<Mutant> createListMutant(CtMethod method) {
        List<Mutant> mutants = new ArrayList<Mutant>();
        String returnType = "";
        try {
            returnType = method.getReturnType().getName();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        if(returnType.equals("void") || returnType.equals("Void")){
            if(method.getLongName().contains(".main(java.lang.String[])")){ // don't replace main method body
                //Check if it is right (before was just "return;")
                return null;
            }
            mutants.add(new Mutant(method,null, this));
//          System.out.println("MUTANT antoine: "+method.getName()+":"+"void");
        }
        return mutants;
    }

    @Override
    public void doMutate(Mutant mutant) {
        try {
            mutant.getCtMethod().setBody("{}");
            this.write(mutant.getCtMethod().getDeclaringClass());
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }
}
