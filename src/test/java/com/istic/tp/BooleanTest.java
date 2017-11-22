package com.istic.tp;

import com.istic.tp.mutant.Mutant;
import com.istic.tp.mutator.ArithMutator;
import com.istic.tp.mutator.BooleanMethodMutator;
import com.istic.tp.mutator.Mutator;
import com.istic.tp.target.ProjectTarget;
import javassist.CtMethod;
import javassist.bytecode.CodeIterator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class BooleanTest {

    CtMethod method;

    @Before
    public void initProjectTarget() {
        ProjectTarget target = new ProjectTarget(".");
        method = target.getMethod("com.istic.tp.mock.BooleanMethodMock", "isSuperior");

    }

    @Test
    public void areMutantsRightForBooleanMethodMock() {
        assertNotNull(method);
        // Init Boolean mutator
        List<Mutator> mutators = new ArrayList<Mutator>();
        mutators.add(new BooleanMethodMutator());

        //Search for mutants for the method
        Scanner scanner = new Scanner(mutators);
        List<Mutant> mutants = new ArrayList<>();
        mutants.addAll(scanner.scan(method));

        //Check if there is only one mutant
        //(with BooleanMethodMutator) for the method isSuperior
        assertEquals(mutants.size(), 1);


        // Check the info of the mutants
        for (Mutant mut: mutants) {
            // Check that it is a Boolean mutator so no index
            assertNull(mut.getIndex());
            // Same object : method
            assertTrue(mut.getCtMethod().equals(method));
            // Same name
            assertTrue(mut.getInitial().getName().equals(method.getName()));
        }
    }

    @Test
    public void testMutate() {

        assertNotNull(method);
        // Init Boolean mutator
        List<Mutator> mutators = new ArrayList<Mutator>();
        mutators.add(new BooleanMethodMutator());

        //Search for mutants for the method
        Scanner scanner = new Scanner(mutators);
        List<Mutant> mutants = new ArrayList<>();
        mutants.addAll(scanner.scan(method));


        CodeIterator ci = method.getMethodInfo().getCodeAttribute().iterator();
        //assertEquals(initBytecode,ci.byteAt(index));
        for (Mutant mutant : mutants) {
            mutant.doMutate();
        }
        // Check the mutation
        ci = method.getMethodInfo().getCodeAttribute().iterator();
        // check the "true"
        assertEquals(4,ci.byteAt(0));
        // check the "return"
        assertEquals(172,ci.byteAt(1));

        for (Mutant mutant : mutants) {
            mutant.revert();
        }

    }


}

