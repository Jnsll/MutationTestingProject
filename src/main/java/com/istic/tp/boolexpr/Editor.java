package com.istic.tp.boolexpr;

import javassist.*;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Mnemonic;
import javassist.expr.*;

import java.io.IOException;
import java.net.URL;


public class Editor {

    public void editor(URL[] urls,String path) throws NotFoundException, CannotCompileException, IOException, BadBytecode {

        ClassPool pool = ClassPool.getDefault();
        for (int i = 0; i < urls.length; i++) {
            try {

                pool.insertClassPath(urls[i].getFile());
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        CtClass cc = pool.get("fr.istic.vv.afe.MathAfe");
        MethodInfo info = cc.getDeclaredMethod("add").getMethodInfo();

        CodeIterator ci = info.getCodeAttribute().iterator();
        while (ci.hasNext()) {
            int index = ci.next();
            int op = ci.byteAt(index);
            if(Mnemonic.OPCODE[op].equals("iadd")){

                   ci.writeByte(100,index);
            }

        }

        cc.writeFile(path);



    }


    private void getLatestArg(MethodCall call) throws BadBytecode {



        MethodInfo info = ((CtMethod)call.where()).getMethodInfo();




    }

}
