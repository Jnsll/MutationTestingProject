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
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(outputDirectory+"/report.md"), "utf-8"))) {
            writer.write("# Test Report\n");
            for (Mutant mutant : mutants) {
                mutant.doMutate();
                System.out.println(mutant);
                writer.write("## " + mutant.toString() + "\n");
                writer.write(projectTarget.launchTest()); // <-- new
                //projectTarget.launchTest(writer);  // <-- old
                mutant.revert();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
