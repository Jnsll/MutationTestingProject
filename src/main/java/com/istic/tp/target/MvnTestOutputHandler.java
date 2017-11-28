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
        //TODO : better parser
        if(line.contains("<<< FAILURE! -")) { // begin of information error
            listError.add(line);
            inError = true;
        }else if(line.startsWith("Running")){ // pass to other test
            inError = false;
        }else if(inError){ // continue information error
            if(!line.isEmpty()) {
                listError.add(line);
            }
        }
    }
}
