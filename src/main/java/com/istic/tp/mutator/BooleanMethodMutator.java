package com.istic.tp.mutator;

import com.istic.tp.mutant.Mutant;
import javassist.CannotCompileException;
import javassist.CtMethod;

import java.util.ArrayList;
import java.util.List;

public class BooleanMethodMutator extends Mutator {


    @Override
    public List<Mutant> createListMutant(final CtMethod method) {
        List<Mutant> mutants = new ArrayList<Mutant>();

        try {
            String returnType;
            returnType = method.getReturnType().getName();
            if (returnType.equalsIgnoreCase("boolean")) {
                mutants.add(new Mutant(method,null, this));
//              System.out.println("MUTANT june: "+method.getName()+":"+"boolean");
            }
        }catch(Throwable exc) {
            System.out.println("Oh, no! Something went wrong.");
            System.out.println(exc.getMessage());
            exc.printStackTrace();
        }
        return mutants;
    }


    @Override
    public void doMutate(Mutant mutant) {
        try {
            mutant.getCtMethod().setBody("{return true;}");
            this.write(mutant.getCtMethod().getDeclaringClass());
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }
}
