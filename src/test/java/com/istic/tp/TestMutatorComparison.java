package com.istic.tp;

import com.istic.tp.mutant.Mutant;
import com.istic.tp.mutator.*;
import com.istic.tp.target.ProjectTarget;
import javassist.CtMethod;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class TestMutatorComparison extends TestCase {

    public void testComparison() {
        ProjectTarget projectTarget = new ProjectTarget(".");
        CtMethod ctMethod = projectTarget.getMethod("com.istic.tp.mock.MockComparison", "isGreater");
        assertNotNull(ctMethod);

        List<Mutator> mutators = new ArrayList<Mutator>();
        mutators.add(new ComparisonMutator());
        assertEquals(1, mutators.size());

        Scanner scanner = new Scanner(mutators);
        List<Mutant> mutants = scanner.scan(ctMethod);
        assertEquals(1, mutants.size());

        Mutant mutant = mutants.get(0);
        assertEquals(mutant.getInitial().getName(), mutant.getCtMethod().getName());
    }

}
