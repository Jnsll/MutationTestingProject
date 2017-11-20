package com.istic.tp;

import com.istic.tp.mutant.Mutant;
import com.istic.tp.target.ProjectTarget;

import java.util.List;

public class Executor {


    /**
     * execute one by one mutants, launch test after every mutation and return to initial byteCode .
     * @param mutants list of mutants
     * @param projectTarget launch test
     */
    public void execute(List<Mutant> mutants, ProjectTarget projectTarget) {

        for(Mutant mutant : mutants){
            mutant.doMutate();
            System.out.println(mutant);
            projectTarget.launchTest();
            mutant.revert();
        }
    }


}
