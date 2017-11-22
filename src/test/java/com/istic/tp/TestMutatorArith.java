package com.istic.tp;

import com.istic.tp.mock.MockArith;
import com.istic.tp.mutant.Mutant;
import com.istic.tp.mutator.*;
import com.istic.tp.mutator.bcoperator.BCOperatorArith;
import com.istic.tp.target.ProjectTarget;
import javassist.CtMethod;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeIterator;

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
        // {namemethod , number mutant, index, bytecode, bytecode mutate }
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    // primary
                    {"addInt", 1,2,96,100}, {"mulInt", 1,2,104,108}, {"divInt", 1,2,108,104}, {"subInt", 1,2, 100,96},
                    {"addLong", 1,2,97,101}, {"mulLong", 1,2,105,109}, {"divLong", 1,2,109,105}, {"subLong", 1,2, 101,97},
                    {"addFloat", 1,2,98,102}, {"mulFloat", 1,2,106,110}, {"divFloat", 1,2,110,106}, {"subFloat", 1,2, 102,98},
                    {"addDouble", 1,2,99,103}, {"mulDouble", 1,2,107,111}, {"divDouble", 1,2,111,107}, {"subDouble", 1,2, 103,99},
                    // object
                    {"addIntObject", 1,8,96,100}, {"mulIntObject", 1,8,104,108}, {"divIntObject", 1,8,108,104}, {"subIntObject", 1,8, 100,96},
                    {"addLongObject", 1,8,97,101}, {"mulLongObject", 1,8,105,109}, {"divLongObject", 1,8,109,105}, {"subLongObject", 1,8, 101,97},
                    {"addFloatObject", 1,8,98,102}, {"mulFloatObject", 1,8,106,110}, {"divFloatObject", 1,8,110,106}, {"subFloatObject", 1,8, 102,98},
                    {"addDoubleObject", 1,8,99,103}, {"mulDoubleObject", 1,8,107,111}, {"divDoubleObject", 1,8,111,107}, {"subDoubleObject", 1,8, 103,99},
            });
        }

        @Parameterized.Parameter
        public String nameMethod;

        @Parameterized.Parameter(1)
        public int numberMutant;

        @Parameterized.Parameter(2)
        public int index;

        @Parameterized.Parameter(3)
        public int initBytecode;

        @Parameterized.Parameter(4)
        public int mutateBytecode;

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

            assertEquals(index, mutant.getIndex().intValue());

        }

        @org.junit.Test
        public void testMutate() {
            ProjectTarget target = new ProjectTarget(".");
            CtMethod method = target.getMethod("com.istic.tp.mock.MockArith", nameMethod);
            assertNotNull(method);
            List<Mutator> mutators = new ArrayList<Mutator>();
            mutators.add(new ArithMutator());

            Scanner scanner = new Scanner(mutators);

            List<Mutant> mutants = scanner.scan(method);

            assertEquals(numberMutant, mutants.size());

            CodeIterator ci = method.getMethodInfo().getCodeAttribute().iterator();
            assertEquals(initBytecode,ci.byteAt(index));
            for (Mutant mutant : mutants) {
                mutant.doMutate();
            }
            ci = method.getMethodInfo().getCodeAttribute().iterator();
            assertEquals(mutateBytecode,ci.byteAt(index));
            //verif the changement


            for (Mutant mutant : mutants) {
                mutant.revert();
            }

            ci = method.getMethodInfo().getCodeAttribute().iterator();
            assertEquals(initBytecode,ci.byteAt(index));







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