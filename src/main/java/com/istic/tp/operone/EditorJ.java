package com.istic.tp.operone;

import javassist.*;

import java.net.MalformedURLException;
import java.net.URL;

public class EditorJ {

    public void editor(URL[] urls) throws NotFoundException, CannotCompileException, MalformedURLException {
        ClassPool pool = ClassPool.getDefault();
        for (int i = 0; i < urls.length; i++) {
            try {

                pool.insertClassPath(urls[i].getFile());
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        CtClass cc = pool.get("fr.istic.vv.june");
        CtMethod cm = cc.getDeclaredMethod("doesAEqualTen");
        System.out.println(cm.getLongName());
    }
}
