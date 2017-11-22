package com.istic.tp;

import com.istic.tp.mutant.Mutant;
import com.istic.tp.mutator.ArithMutator;
import com.istic.tp.mutator.ComparisonMutator;
import com.istic.tp.mutator.Mutator;
import com.istic.tp.target.ProjectTarget;
import javassist.CtMethod;
import javassist.bytecode.CodeIterator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class TestMutatorComparison {

    // /!\ important
    // --> in bytecode, logic is inverse
    // bytecode test if value is not opposite of operator
    // ----------------
    // for operator '<' if '>=' IS OK --> result KO
    // for operator '>' if '<=' IS OK --> result KO
    // for operator '<=' if '>' IS OK --> result KO
    // for operator '>=' if '<' IS OK --> result KO
    // ----------------
    // so in parameters (initBytecode and mutateBytecode),
    // added inverse bytecode of real operator.

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "intIsGreater", 1, 8, 164, 161 },
                { "intIsLess", 1, 8, 162, 163 },
                { "intIsGreaterOrEqual", 1, 8, 161, 164 },
                { "intIsLessOrEqual", 1, 8, 163, 162 }
        });
    }

    @Parameterized.Parameter
    public String nameMethod;

    @Parameterized.Parameter(1)
    public int numberMutant;

    @Parameterized.Parameter(2)
    public int indexPosition;

    @Parameterized.Parameter(3)
    public int initBytecode;

    @Parameterized.Parameter(4)
    public int mutateBytecode;

    @Test
    public void testMutant() {
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

    @Test
    public void testDoMutate() {
        ProjectTarget projectTarget = new ProjectTarget(".");
        CtMethod ctMethod = projectTarget.getMethod("com.istic.tp.mock.MockComparison", nameMethod);
        assertNotNull(ctMethod);

        List<Mutator> mutators = new ArrayList<Mutator>();
        mutators.add(new ComparisonMutator());
        assertEquals(1, mutators.size());

        Scanner scanner = new Scanner(mutators);
        List<Mutant> mutants = scanner.scan(ctMethod);

        assertEquals(numberMutant, mutants.size());

        CodeIterator ci = ctMethod.getMethodInfo().getCodeAttribute().iterator();

        assertEquals(initBytecode, ci.byteAt(indexPosition));

        for (Mutant mutant : mutants) {
            mutant.doMutate();
        }

        ci = ctMethod.getMethodInfo().getCodeAttribute().iterator();
        assertEquals(mutateBytecode, ci.byteAt(indexPosition));

        for (Mutant mutant : mutants) {
            mutant.revert();
        }

        ci = ctMethod.getMethodInfo().getCodeAttribute().iterator();
        assertEquals(initBytecode, ci.byteAt(indexPosition));
    }

}
