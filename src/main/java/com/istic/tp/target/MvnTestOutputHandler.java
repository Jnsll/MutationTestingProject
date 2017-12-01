package com.istic.tp.target;

import org.apache.maven.shared.invoker.InvocationOutputHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Parse the output of Mvntest
 */
public class MvnTestOutputHandler implements InvocationOutputHandler {
    /**
     * if true then there are informations errors
     */
    private Boolean inError;

    /**
     * list of error for one mutate
     */
    private List<String> listError = new ArrayList<>();

    public MvnTestOutputHandler() {
        inError=false;
    }

    public List<String> getListError() {
        return listError;
    }

    @Override
    public void consumeLine(String line) {
        System.out.println("[ALL]"+line);
        //TODO : better parser
        if(line.startsWith("Results :")) { // begin of information error
            listError.add("### "+line+"\n");
            inError = true;
        }else if(inError && line.startsWith("Tests run:")){ // pass to other test
            listError.add("\n"+line+"\n");
            inError = false;
        }else if(inError){ // continue information error
            if(!line.isEmpty()) {
                listError.add(line);
            }
        }
    }
}
