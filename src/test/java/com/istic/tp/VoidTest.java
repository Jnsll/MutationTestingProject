package com.istic.tp;

import com.istic.tp.mutant.Mutant;
import com.istic.tp.mutator.BooleanMethodMutator;
import com.istic.tp.mutator.Mutator;
import com.istic.tp.mutator.VoidMethodMutator;
import com.istic.tp.target.ProjectTarget;
import javassist.CtMethod;
import javassist.bytecode.CodeIterator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class VoidTest {

    ProjectTarget target;

    @Before
    public void initProjectTarget() {
        target = new ProjectTarget(".");

    }

    @Test
    public void areMutantsRightForVoidMethodMock() {
        CtMethod method = target.getMethod("com.istic.tp.mock.VoidMethodMock", "incrementsCount");

        assertNotNull(method);
        // Init Boolean mutator
        List<Mutator> mutators = new ArrayList<Mutator>();
        mutators.add(new VoidMethodMutator());

        //Search for mutants for the method
        Scanner scanner = new Scanner(mutators);
        List<Mutant> mutants = new ArrayList<>();
        mutants.addAll(scanner.scan(method));

        //Check if there is only one mutant
        //(with VoidMethodMutator) for the method incrementsCount
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
        CtMethod method = target.getMethod("com.istic.tp.mock.VoidMethodMock", "incrementsCount");

        assertNotNull(method);
        // Init Void mutator
        List<Mutator> mutators = new ArrayList<Mutator>();
        mutators.add(new VoidMethodMutator());

        //Search for mutants for the method
        Scanner scanner = new Scanner(mutators);
        List<Mutant> mutants = new ArrayList<>();
        mutants.addAll(scanner.scan(method));
        
        for (Mutant mutant : mutants) {
            mutant.doMutate();
        }
        // Check the mutation
        boolean empty = method.isEmpty();
        // check if the body is empty
        assertTrue(empty);


        for (Mutant mutant : mutants) {
            mutant.revert();
        }

    }

    @Test
    public void areMutantsWrongForMethodMock() {
        CtMethod method = target.getMethod("com.istic.tp.mock.VoidMethodMock", "methodBoolean");
        assertNotNull(method);
        // Init Boolean mutator
        List<Mutator> mutators = new ArrayList<Mutator>();
        mutators.add(new VoidMethodMutator());

        //Search for mutants for the method
        Scanner scanner = new Scanner(mutators);
        List<Mutant> mutants = new ArrayList<>();
        mutants.addAll(scanner.scan(method));

        assertEquals(mutants.size(), 0);


    }

    @Test
    public void testNotMutate() {
        CtMethod method = target.getMethod("com.istic.tp.mock.VoidMethodMock", "methodBoolean");
        assertNotNull(method);

        // Init Boolean mutator
        List<Mutator> mutators = new ArrayList<Mutator>();
        mutators.add(new VoidMethodMutator());

        //Search for mutants for the method
        Scanner scanner = new Scanner(mutators);
        List<Mutant> mutants = new ArrayList<>();
        mutants.addAll(scanner.scan(method));

        assertEquals(mutants.size(), 0);

        assertFalse(method.isEmpty());




    }


}
