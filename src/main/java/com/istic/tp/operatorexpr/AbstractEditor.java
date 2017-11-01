package com.istic.tp.operatorexpr;

import javassist.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Editor of methods
 */
public abstract class AbstractEditor {
    /**
     * path of class from target project
     */
    final String path;

    /**
     *ClassPool of target project
     */
    final ClassPool pool;

    public AbstractEditor(String path, URL[] urls) {
        this.path = path+"/target/classes/";

        this.pool = ClassPool.getDefault();

        for (int i = 0; i < urls.length; i++) {
            try {

                pool.insertClassPath(urls[i].getFile());
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * edit all classes
     */
    public void editor() {

        final File folder = new File(path);
        try {
            this.recursiveFileEditor(folder);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    /**
     * edit one class
     * @param nameclass
     */
    public void editor(final String nameclass)  {



        try {
            CtClass cc = pool.get(nameclass);
            for(CtMethod ct : cc.getDeclaredMethods()){
                replace(ct);
            }
            cc.writeFile(path);
            cc.defrost(); // modifiable again
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }


    }

    /**
     *
     * @param method
     */
    protected abstract void replace(final CtMethod method) ;


    /**
     *
     * @param folder
     * @throws ClassNotFoundException
     */
    protected void recursiveFileEditor(final File folder) throws ClassNotFoundException {

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                recursiveFileEditor(fileEntry);
            } else {


                String name = fileEntry.toString()
                        .replace(path,"")
                        .replaceAll(".class","")
                        .replaceAll("/",".");
                editor(name);



            }
        }
    }
}
