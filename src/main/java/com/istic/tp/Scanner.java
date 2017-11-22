package com.istic.tp;

import com.istic.tp.mutant.Mutant;
import com.istic.tp.mutator.Mutator;
import javassist.CtMethod;

import java.util.ArrayList;
import java.util.List;

public class Scanner {

    List<Mutator> mutators = new ArrayList<Mutator>();

    public Scanner(List<Mutator> mutators) {
        this.mutators = mutators;
    }

    public List<Mutant> scan(final CtMethod method) {
        List<Mutant> mutants = new ArrayList<Mutant>();
        for (Mutator mutator: mutators) {
            mutants.addAll(mutator.createListMutant(method));
        }
        return mutants;
    }

}
