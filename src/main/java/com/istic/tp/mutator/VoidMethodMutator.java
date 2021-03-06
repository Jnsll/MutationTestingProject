package com.istic.tp.mutator;

import com.istic.tp.mutant.Mutant;
import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class VoidMethodMutator extends Mutator {


    @Override
    public List<Mutant> createListMutant(CtMethod method) {
        List<Mutant> mutants = new ArrayList<Mutant>();
        String returnType = "";
        try {
            returnType = method.getReturnType().getName();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        if(returnType.equalsIgnoreCase("void")){
            if(!method.getLongName().contains(".main(java.lang.String[])")){ // don't replace main method body
               mutants.add(new Mutant(method,null, this));
            }

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

    @Override
    public void revert(Mutant mutant) {

        try {
            mutant.getCtMethod().setBody(mutant.getInitial(),null);
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

        this.write(mutant.getInitial().getDeclaringClass());

    }
}
