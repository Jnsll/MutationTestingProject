package com.istic.tp;

import com.istic.tp.mutant.Mutant;
import com.istic.tp.mutator.Mutator;
import java.util.List;

public class Executor {


    public void execute(List<Mutant> mutants) {
        for(Mutant mutant: mutants) {
            // the first two lines could be put in the Mutant class
            // -> Enhancement
            Mutator mutator = mutant.getMutator();
            mutator.doMutate(mutant);

            // launchTest() -> méthode à mettre où ? Ici, dans classe Executor ?

            mutator.revert(mutant.getCtMethod(),mutant.getInitial());
        }
    }


}
