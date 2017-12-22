package com.istic.tp.mutant;

import com.istic.tp.mutator.Mutator;
import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.CtNewMethod;

public class Mutant {
    /**
     * copy of Method before replace. can be null
     */
    private CtMethod initial;
    /**
     * Method to mutate
     */
    private CtMethod ctMethod;
    /**
     * the Position of byteCode. can be null
     */
    private Integer index;
    /**
     * the type of mutator who will apply this mutant
     */
    private final Mutator mutator;


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


    /**
     * change the byteCode
     */
    public void doMutate(){
        this.mutator.doMutate(this);
    }
    /**
     * return to initial Bytecode
     */
    public void revert(){
        this.mutator.revert(this);
    }

    public CtMethod getInitial() {
        return initial;
    }

    public CtMethod getCtMethod() {
        return ctMethod;
    }

    public Integer getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "Mutant{" +
                "Class=" + ctMethod.getDeclaringClass().getName() +
                ", Method=" + ctMethod.getName() +
                ", index=" + index +
                ", Mutator=" + this.mutator.getClass().getSimpleName() +
                '}';
    }


}
