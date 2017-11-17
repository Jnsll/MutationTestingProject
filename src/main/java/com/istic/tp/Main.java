package com.istic.tp;

import com.istic.tp.mutator.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main( String[] args ) throws Throwable {

        List<Mutator> mutators = new ArrayList<Mutator>();
        mutators.add(new BooleanMethodMutator());
        mutators.add(new VoidMethodMutator());
        mutators.add(new ArithMutator());
        mutators.add(new ComparisonMutator());

        Scanner scanner = new Scanner(mutators);

        //Parcours du Target Project
        //Jusqu'à arriver au niveau des méthodes
        //Puis boucle sur les méthodes

        //List<Mutant> mutants = scanner.scan(method);


        //Partie Execution
        Executor executor = new Executor();
        //executor.execute(mutants);

    }
}
