package com.istic.tp;

import com.istic.tp.mutant.Mutant;
import com.istic.tp.mutator.Mutator;
import java.util.List;

public class Executor {
    // antoine : no need. we can't test every step if we put this loop here

    public void execute(List<Mutant> mutants) {
        for(Mutant mutant: mutants) {
            // the first two lines could be put in the Mutant class
            // -> Enhancement
            //mutant.getMutator().doMutate(mutant);

            // launchTest() -> méthode à mettre où ? Ici, dans classe Executor ?

            //mutant.getMutator().revert(mutant);
        }
    }


}
