package com.istic.tp;

import com.istic.tp.mutant.Mutant;
import com.istic.tp.target.ProjectTarget;

import java.io.*;
import java.util.List;

public class Executor {


    /**
     * execute one by one mutants, launch test after every mutation and return to initial byteCode .
     * @param mutants list of mutants
     * @param projectTarget launch test
     */
    public void execute(List<Mutant> mutants, ProjectTarget projectTarget,String outputDirectory) {
        int killed = 0;
        int inProgress = 0;
        int total= mutants.size();

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(outputDirectory+"/report.md"), "utf-8"))) {
            writer.write("# Test Report\n");
            for (Mutant mutant : mutants) {
                mutant.doMutate();
                System.out.println(mutant);
                writer.write("## " + mutant.toString() + "\n");
                String result = projectTarget.launchTest();
                //TODO : improve
                if(result.startsWith("The mutant was killed")){
                    System.out.println("Result : killed");
                    killed++;
                }else{
                    System.out.println("Result : not killed");
                }


                writer.write(result);
                mutant.revert();
                inProgress++;
                System.out.println(inProgress +" on "+total);
                System.out.println("kill : "+killed);
                System.out.println((new Float((float)killed/inProgress))*100+"% killed");
            }
            writer.write("\n\n"+killed+ " killed on "+total+" mutants");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
