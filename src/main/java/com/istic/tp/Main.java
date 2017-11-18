package com.istic.tp;

import com.istic.tp.mutant.Mutant;
import com.istic.tp.mutator.*;
import com.istic.tp.target.ProjectTarget;
import javassist.CtMethod;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main( String[] args ) throws Throwable {
        if(args.length != 1){
            System.err.println("need a location of target project");
            return;
        }

        File file = new File(args[0]);

        if(!file.exists()){
            System.err.println("the directory doesn't exist");
            return;
        }

        if(!file.isDirectory()){
            System.err.println("need a directory, not a file");
            return;
        }

        ProjectTarget projectTarget = new ProjectTarget(args[0]);

        if(!projectTarget.build()){
            return;
        }

        // Init all mutator
        List<Mutator> mutators = new ArrayList<Mutator>();
        mutators.add(new BooleanMethodMutator());
        mutators.add(new VoidMethodMutator());
        mutators.add(new ArithMutator());
        mutators.add(new ComparisonMutator());

        // find mutant
        Scanner scanner = new Scanner(mutators);
        List<Mutant> mutans = new ArrayList<>();
        Set<CtMethod> methods = projectTarget.getMethods();
        for(CtMethod method : methods){
            mutans.addAll(scanner.scan(method));
        }

        // apply mutant
        for(Mutant mutant : mutans){
           mutant.doMutate(); // c'est plus jolie qu'un executor
                              //et ça nous permet de jouer plus facilement dans les tests
           System.out.println(mutant);
           projectTarget.launchTest();
           mutant.revert();
        }

        //
        projectTarget.clean();


    }
}
