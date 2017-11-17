package com.istic.tp.mutant;

import com.istic.tp.mutator.Mutator;
import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.CtNewMethod;

public class Mutant {
    /**
     * copy of Method before replace byteCode
     */
    private CtMethod initial;
    /**
     * Method to mutate
     */
    private CtMethod ctMethod;
    /**
     * Position of byteCode. can be null
     */
    private Integer index;

    private Mutator mutator;


    public Mutant(CtMethod ctMethod, Integer index, Mutator mutator) {
        this.ctMethod = ctMethod;
        try {
            this.initial = CtNewMethod.copy(ctMethod,ctMethod.getDeclaringClass(),null);
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
        this.index = index;
        this.mutator = mutator;
    }

    public CtMethod getCtMethod() {
        return ctMethod;
    }

    public Integer getIndex() {
        return index;
    }

    public CtMethod getInitial() {
        return initial;
    }

    public Mutator getMutator() {
        return mutator;
    }

    public void setMutator(Mutator mutator) {
        this.mutator = mutator;
    }

    @Override
    public String toString() {
        return "Mutant{" +
                "ctClass=" + ctMethod.getDeclaringClass().getName() +
                ", ctMethod=" + ctMethod.getName() +
                ", index=" + index +
                '}';
    }
}
