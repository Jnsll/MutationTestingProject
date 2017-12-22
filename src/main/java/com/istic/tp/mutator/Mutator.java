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


    /**
     * return to initial Bytecode
     * @param mutant
     */
    public abstract void revert(Mutant mutant) ;

    /**
     * Writes a class file represented by this CtClass cc
     * @param cc class to modify
     */
    protected void write(CtClass cc) {

        try {
            String url = cc.getURL().toString();
            int index = 0;
            String folderSrc = "/target/classes/";
            String folderTest = "/target/test-classes/";
            if(url.contains(folderSrc)){
               index = url.lastIndexOf(folderSrc) +folderSrc.length();
            }else if(url.contains(folderTest)){
                index = url.lastIndexOf(folderTest) + folderTest.length();
            }
            String name = url.substring(0,index).replaceAll("file:","");
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



}
