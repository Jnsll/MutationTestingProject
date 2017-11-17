package com.istic.tp.editor;

import com.istic.tp.mutant.Mutant;
import com.istic.tp.target.ProjectTarget;
import javassist.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Editor of methods
 */
public abstract class AbstractEditor {
    /**
     * target Project
     */
    final protected ProjectTarget target;

    /**
     *ClassPool of target project
     */
    final protected ClassPool pool;
    /**
     * list of mutants. it's feed by the method build
     */
    final List<Mutant> mutants;

    public AbstractEditor(ProjectTarget target) {
        this.target = target;
        this.mutants = new ArrayList<>();
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
     * scan all classes
     */
    public void scan() {

        final File folder = new File(this.target.getPathsrc());
        try {
            this.recursiveFileBuild(folder);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    /**
     * scan one class
     * @param nameclass
     */
    public void scan(final String nameclass)  {

        try {
            CtClass cc = pool.get(nameclass);
            for(CtMethod ct : cc.getDeclaredMethods()){

                createListMutant(ct);
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

    }

    public void launch() {

        for(Mutant mutant : mutants){
            System.out.println(mutant);
            this.replace(mutant);
            this.target.launchTest();
            this.revert(mutant.getCtMethod(),mutant.getInitial());
        }
    }

    /**
     * Writes a class file represented by this CtClass cc
     * @param cc class modif
     */
    protected void write(CtClass cc) {
        try {
            cc.writeFile(this.target.getPathsrc());
            cc.defrost(); // modifiable again

        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * create list of mutant in CtMethod method
     * @param method
     */

    protected abstract void createListMutant(final CtMethod method) ;

    /**
     *
     * @param mutant
     */
    protected abstract void replace(final Mutant mutant) ;

    /**
     * revert CtMethod ct before update
     * @param ct
     */
    protected void revert(CtMethod ct,CtMethod copy) {

        try {
            ct.setBody(copy,null);
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

        this.write(ct.getDeclaringClass());

    }



    /**
     *
     * @param folder
     * @throws ClassNotFoundException
     */
    protected void recursiveFileBuild(final File folder) throws ClassNotFoundException {

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                recursiveFileBuild(fileEntry);
            } else {


                String name = fileEntry.toString()
                        .replace(this.target.getPathsrc(),"")
                        .replaceAll(".class","")
                        .replaceAll("/",".");
                scan(name);



            }
        }
    }
}
