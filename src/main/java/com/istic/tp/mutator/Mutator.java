package com.istic.tp.mutator;

import com.istic.tp.mutant.Mutant;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.io.IOException;
import java.util.List;

public abstract class Mutator {


    /**
     * create list of mutant in CtMethod method
     * @param method
     */

    public abstract List<Mutant> createListMutant(final CtMethod method) ;

    /**
     * Modify the byteCode
     * @param mutant
     */
    public abstract void doMutate(Mutant mutant);


    // june : We have to check if it is right to put this method here
    // antoine : it's okay for me. Only Mutator change the bytecode
    /**
     * Writes a class file represented by this CtClass cc
     * @param cc class to modify
     */
    protected void write(CtClass cc) {
        try {

            String name = cc.getURL().toString()
                    .replaceAll(cc.getName(),"")
                    .replaceAll("file:","")
                    .replaceAll("\\.class","");

            cc.writeFile(name);
            cc.defrost(); // modifiable again

        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * return to initial Bytecode
     * @param mutant
     */
    public void revert(Mutant mutant) {

        try {
            mutant.getCtMethod().setBody(mutant.getInitial(),null);
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

        this.write(mutant.getInitial().getDeclaringClass());

    }

}
