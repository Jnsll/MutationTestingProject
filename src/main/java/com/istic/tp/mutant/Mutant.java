package com.istic.tp.mutant;

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



    public Mutant(CtMethod ctMethod, Integer index) {
        this.ctMethod = ctMethod;
        try {
            this.initial = CtNewMethod.copy(ctMethod,ctMethod.getDeclaringClass(),null);
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
        this.index = index;
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

    @Override
    public String toString() {
        return "Mutant{" +
                "ctClass=" + ctMethod.getDeclaringClass().getName() +
                ", ctMethod=" + ctMethod.getName() +
                ", index=" + index +
                '}';
    }
}
