package com.istic.tp.operatorexpr;

import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.NotFoundException;

public class EditorVoidMethod extends AbstractEditor {
    public EditorVoidMethod(String path) {
        super(path);
    }

    @Override
    protected void replace(CtMethod method) {
        String returnType = "";
        try {
            returnType = method.getReturnType().getName();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
       
        if(returnType.equals("void") || returnType.equals("Void")){
            if(method.getLongName().contains(".main(java.lang.String[])")){ // don't replace main method body
                return;
            }
            try {
                method.setBody("{}");
            } catch (CannotCompileException e) {
                e.printStackTrace();
            }
        }

    }
}
