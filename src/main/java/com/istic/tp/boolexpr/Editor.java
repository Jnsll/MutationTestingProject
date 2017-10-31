package com.istic.tp.boolexpr;

import javassist.*;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Opcode;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class Editor {

    public void editor(URL[] urls) throws NotFoundException, CannotCompileException, IOException {

        ClassPool pool = ClassPool.getDefault();
        for (int i = 0; i < urls.length; i++) {
            try {

                pool.insertClassPath(urls[i].getFile());
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        CtClass cc = pool.get("fr.istic.vv.afe.MathAfe");
        CtMethod cm = cc.getDeclaredMethod("add");
        System.out.println(cm.getLongName());
        cm.insertBefore("{x=x+5;}");
        cc.writeFile("/home/aferey/Documents/VV/MockMathSoftware/target/classes/");

      /* cm.instrument(
                new ExprEditor() {
                    public void edit(MethodCall m)
                            throws CannotCompileException
                    {
                        System.out.println(m.getMethodName() + " line: "
                                + m.getLineNumber());
                        try {
                            getLatestArg(m);
                        } catch (BadBytecode badBytecode) {
                            badBytecode.printStackTrace();
                        }
                    }

                });*/
    }


    private void getLatestArg(MethodCall call) throws BadBytecode {



        MethodInfo info = ((CtMethod)call.where()).getMethodInfo();
        CodeIterator iterator = info.getCodeAttribute().iterator();
        System.out.println("merde");
        while (iterator.hasNext()) {
            int pos = iterator.next();
            System.out.println(iterator.get());
            System.out.println(pos);
        }



    }

}
