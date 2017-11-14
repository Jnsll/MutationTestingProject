package com.istic.tp.operatorexpr;

import com.istic.tp.ProjectTarget;
import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.Mnemonic;

public class EditorVoidMethod extends AbstractEditor {
    public EditorVoidMethod(ProjectTarget target) {
        super(target);
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
            CodeIterator ci = method.getMethodInfo().getCodeAttribute().iterator();
            while (ci.hasNext()) {
                int index = 0;
                try {

                    index = ci.next();
                    System.out.println("void:"+method.getName()+":"+index);
                } catch (BadBytecode badBytecode) {
                    badBytecode.printStackTrace();
                }

            }
        }



    }
}
