package com.istic.tp;

import com.istic.tp.mutant.Mutant;
import com.istic.tp.mutator.*;
import com.istic.tp.target.ProjectTarget;
import javassist.CtMethod;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main( String[] args ) throws Throwable {

        if(args.length != 2){
            System.err.println("needs 2 arguments : <location of the target project> <path to the directory for the report output>");
            return;
        }

        File directoryProject = new File(args[0]);
        File directoryOutput = new File(args[1]);
        if(!directoryProject.exists()){
            System.err.println("the directory target project doesn't exist");
            return;
        }

        if(!directoryProject.isDirectory()){
            System.err.println("needs a directory for target project, not a file");
            return;
        }

        if(!directoryOutput.exists()){
            System.err.println("the directory for the output report doesn't exist");
            return;
        }

        if(!directoryProject.isDirectory()){
            System.err.println("needs a directory for the output report, not a file");
            return;
        }

        ProjectTarget projectTarget = new ProjectTarget(args[0]);
        System.out.println("[INFO] - Target project is building ...");
        if(!projectTarget.build()){
            return;
        }

        // Init all mutator
        List<Mutator> mutatorsByByte = new ArrayList<Mutator>();

        List<Mutator> mutatorsByCode = new ArrayList<Mutator>();
        mutatorsByCode.add(new BooleanMethodMutator());
        mutatorsByCode.add(new VoidMethodMutator());
        mutatorsByByte.add(new ArithMutator());
        mutatorsByByte.add(new ComparisonMutator());

        // find mutant
        Scanner scannerByte = new Scanner(mutatorsByByte);
        Scanner scannerCode = new Scanner(mutatorsByCode);
        List<Mutant> mutants = new ArrayList<>();
        Set<CtMethod> methods = projectTarget.getMethods();


        System.out.println("[INFO] - Target project is scanning ...");


        for(CtMethod method : methods){
            mutants.addAll(scannerByte.scan(method));
        }

        for(CtMethod method : methods){
            mutants.addAll(scannerCode.scan(method));
        }

        // apply mutant
        Executor executor = new Executor();
        System.out.println("[INFO] - Execute All mutants");
        executor.execute(mutants,projectTarget,args[1]);

        //
        System.out.println("[INFO] - Target project is cleaning ...");
        projectTarget.clean();


    }
}
