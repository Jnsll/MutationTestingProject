package com.istic.tp.operone;

import javassist.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class EditorJ {

    private String directory;

    public EditorJ(String directory) {
        this.directory = directory;
    }

    public void editor(URL[] urls) throws NotFoundException, CannotCompileException, IOException {
        try {
            ClassPool pool = ClassPool.getDefault();
            for (int i = 0; i < urls.length; i++) {
                try {

                    pool.insertClassPath(urls[i].getFile());
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
            CtClass cc = pool.get("fr.istic.vv.june.OperatorOne");
            CtMethod cm = cc.getDeclaredMethod("doesAEqualTen");
            System.out.println(cm.getLongName());
            cm.setBody("{return true;}");
            cc.writeFile(directory + "/target/classes/");
        }
        catch(Throwable exc) {
            System.out.println("Oh, no! Something went wrong.");
            System.out.println(exc.getMessage());
            exc.printStackTrace();
        }
    }
}
