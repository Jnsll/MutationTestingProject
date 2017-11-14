package com.istic.tp.operatorexpr;

import com.istic.tp.ProjectTarget;
import javassist.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Editor of methods
 */
public abstract class AbstractEditor {
    /**
     * path of class from target project
     */
    final protected ProjectTarget target;

    /**
     *ClassPool of target project
     */
    final protected ClassPool pool;
    /**
     * copy de la methode avant modif
     */
    protected CtMethod copy;

    public AbstractEditor(ProjectTarget target) {
        this.target = target;

        this.pool = ClassPool.getDefault();
        URL[] urls = new URL[0];
        try {
            urls = new URL[]{ new URL("file://"+this.target.getPathsrc()),new URL("file://"+this.target.getPathsrcTest()) };
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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

        final File folder = new File(this.target.getPathsrc());
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
                copy = CtNewMethod.copy(ct,ct.getDeclaringClass(),null);
                replace(ct);
            }
            cc.writeFile(this.target.getPathsrc());
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

    protected void revert(CtMethod ct){
        try {
            ct.setBody(copy,null);
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

    }

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
                        .replace(this.target.getPathsrc(),"")
                        .replaceAll(".class","")
                        .replaceAll("/",".");
                editor(name);



            }
        }
    }
}
