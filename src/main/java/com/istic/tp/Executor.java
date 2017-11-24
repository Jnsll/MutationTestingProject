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
    public void execute(List<Mutant> mutants, ProjectTarget projectTarget) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("report.md"), "utf-8"))) {
            writer.write("# Test Report\n");
            for (Mutant mutant : mutants) {
                mutant.doMutate();
                writer.write("## " + mutant.toString());
                projectTarget.launchTest(writer);
                mutant.revert();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
