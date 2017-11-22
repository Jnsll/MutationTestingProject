package com.istic.tp;

import com.istic.tp.mutant.Mutant;
import com.istic.tp.mutator.ComparisonMutator;
import com.istic.tp.mutator.Mutator;
import com.istic.tp.target.ProjectTarget;
import javassist.CtMethod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestMutatorComparison {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"isGreater", 1, 2}, {"isLess", 1, 2},
                {"isGreaterOrEqual", 1, 2}, {"isLessOrEqual", 1, 2}
        });
    }

    @Parameterized.Parameter
    public String nameMethod;

    @Parameterized.Parameter(1)
    public int numberMutant;

    @Parameterized.Parameter(2)
    public int indexPosition;

    @Test
    public void testComparison() {
        ProjectTarget projectTarget = new ProjectTarget(".");
        CtMethod ctMethod = projectTarget.getMethod("com.istic.tp.mock.MockComparison", nameMethod);
        assertNotNull(ctMethod);

        List<Mutator> mutators = new ArrayList<Mutator>();
        mutators.add(new ComparisonMutator());
        assertEquals(1, mutators.size());

        Scanner scanner = new Scanner(mutators);
        List<Mutant> mutants = scanner.scan(ctMethod);
        assertEquals(numberMutant, mutants.size());

        Mutant mutant = mutants.get(0);
        assertEquals(mutant.getInitial().getName(), mutant.getCtMethod().getName());

        assertEquals(indexPosition, mutant.getIndex().intValue());
    }

}
