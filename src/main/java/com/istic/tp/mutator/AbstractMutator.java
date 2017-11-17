package com.istic.tp.mutator;

import javassist.CtMethod;

public abstract class AbstractMutator {


    /**
     * create list of mutant in CtMethod method
     * @param method
     */

    protected abstract void createListMutant(final CtMethod method) ;
}
