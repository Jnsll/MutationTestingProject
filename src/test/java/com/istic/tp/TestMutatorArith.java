package com.istic.tp;

import com.istic.tp.mutant.Mutant;
import com.istic.tp.mutator.*;
import com.istic.tp.target.ProjectTarget;
import javassist.CtMethod;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class TestMutatorArith {

    @RunWith(Parameterized.class)
    public static class ComponentParamTests {
        // {namemethod , number mutant }
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"addInt", 1}, {"mulInt", 1}, {"divInt", 1}, {"subInt", 1},
                    {"addLong", 1}, {"mulLong", 1}, {"divLong", 1}, {"subLong", 1},
                    {"addFloat", 1}, {"mulFloat", 1}, {"divFloat", 1}, {"subFloat", 1},
                    {"addDouble", 1}, {"mulDouble", 1}, {"divDouble", 1}, {"subDouble", 1}
            });
        }

        @Parameterized.Parameter
        public String nameMethod;

        @Parameterized.Parameter(1)
        public int numberMutant;

        /**
         * test every mutant
         */
        @org.junit.Test
        public void testListMutantSimple() {
            ProjectTarget target = new ProjectTarget(".");
            CtMethod method = target.getMethod("com.istic.tp.mock.MockArith", nameMethod);
            assertNotNull(method);
            List<Mutator> mutators = new ArrayList<Mutator>();
            mutators.add(new ArithMutator());

            Scanner scanner = new Scanner(mutators);

            List<Mutant> mutants = scanner.scan(method);

            assertEquals(numberMutant, mutants.size());
            Mutant mutant = mutants.get(0);

            assertEquals(method, mutant.getCtMethod());

            assertEquals(mutant.getCtMethod().getName(), mutant.getInitial().getName());

            assertEquals(2, mutant.getIndex().intValue());

        }


    }
    public static class ComponentSingleTests {
        /**
         * test on method whith several mutant
         */
        @org.junit.Test
        public void testListMutantFibo() {
            ProjectTarget target = new ProjectTarget(".");
            CtMethod method = target.getMethod("com.istic.tp.mock.MockArith", "fibo");
            assertNotNull(method);
            List<Mutator> mutators = new ArrayList<Mutator>();
            mutators.add(new ArithMutator());

            Scanner scanner = new Scanner(mutators);

            List<Mutant> mutants = scanner.scan(method);

            assertEquals(3, mutants.size());

            for (Mutant mutant : mutants) {
                assertEquals(method, mutant.getCtMethod());
                assertEquals(mutant.getCtMethod().getName(), mutant.getInitial().getName());
            }

        }

    }
}