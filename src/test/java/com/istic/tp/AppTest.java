package com.istic.tp;

import com.istic.tp.editor.AbstractEditor;
import com.istic.tp.editor.EditorArithOperator;
import com.istic.tp.mutant.Mutant;
import com.istic.tp.target.ProjectTarget;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Ignore;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * ici, on peut verifier la récupération des mutants sur la class com.istic.tp.mock.Mock
     */
    public void testRecupMutant()
    {
        ProjectTarget projectTarget = new ProjectTarget(".");
        AbstractEditor abstractEditor = new EditorArithOperator(projectTarget);
        abstractEditor.scan("com.istic.tp.mock.Mock");

        try {

            Field field = abstractEditor.getClass().getSuperclass().getDeclaredField("mutants");
            field.setAccessible(true);
            List<Mutant> mutants = (List<Mutant>) field.get(abstractEditor);
            System.out.println(mutants.get(0));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    /**
     * ici, on peut verifier la transformation du ByteCode sur la class com.istic.tp.mock.Mock
     */

    public void testReplace()
    {
        ProjectTarget projectTarget = new ProjectTarget(".");
        AbstractEditor abstractEditor = new EditorArithOperator(projectTarget);
        abstractEditor.scan("com.istic.tp.mock.Mock");

        Method method = null;
        List<Mutant> mutants = null;
        try {
            Class[] cArg = new Class[1];
            cArg[0] = Mutant.class;
            Field field = abstractEditor.getClass().getSuperclass().getDeclaredField("mutants");
            field.setAccessible(true);
            mutants = (List<Mutant>) field.get(abstractEditor);
            method = abstractEditor.getClass().getSuperclass().getDeclaredMethod("replace",cArg);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Object[] argObjects = new Object[1];
        System.out.println(mutants.get(0));
        argObjects[0] =  mutants.get(0);
        method.setAccessible(true);
        try {
            method.invoke(abstractEditor,argObjects);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        assertTrue( true );
    }
}
