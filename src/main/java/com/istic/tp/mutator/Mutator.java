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

    public abstract void doMutate(Mutant mutant);


    // We have to check if it is right to put this method here
    /**
     * Writes a class file represented by this CtClass cc
     * @param cc class modif
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

    public void revert(CtMethod ct,CtMethod copy) {

        try {
            ct.setBody(copy,null);
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

        this.write(ct.getDeclaringClass());

    }

}
