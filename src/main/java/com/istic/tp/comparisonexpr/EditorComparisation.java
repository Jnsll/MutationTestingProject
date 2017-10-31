package com.istic.tp.comparisonexpr;

import javassist.*;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Mnemonic;
import javassist.expr.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class EditorComparisation {

    private String directory;

    public EditorComparisation(String directory) {
        this.directory = directory;
    }

    public void editor(URL[] urls) throws NotFoundException, CannotCompileException, MalformedURLException {
        ClassPool pool = ClassPool.getDefault();

        for (int i = 0; i < urls.length; i++) {
            try {
                pool.insertClassPath(urls[i].getFile());
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }

        CtClass cc = pool.get("fr.istic.vv.elo.SampleELO");
        CtMethod cm = cc.getDeclaredMethod("isBigger");
        System.out.println(cm.getLongName());

        try {
            getLatestArg(cm.getMethodInfo());
        } catch (BadBytecode badBytecode) {
            badBytecode.printStackTrace();
        }

        // re-write file
        try {
            cc.writeFile(directory + "/target/classes/");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getLatestArg(MethodInfo info) throws BadBytecode {
        CodeIterator iterator = info.getCodeAttribute().iterator();
        while (iterator.hasNext()) {
            int index = iterator.next();
            int op = iterator.byteAt(index);
            if (Mnemonic.OPCODE[op].equals("if_icmple")) {
                iterator.writeByte(161, index);
            }
            System.out.println(Mnemonic.OPCODE[op]);
        }

    }
}
