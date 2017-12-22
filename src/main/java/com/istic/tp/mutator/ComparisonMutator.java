package com.istic.tp.mutator;

import com.istic.tp.mutant.Mutant;
import com.istic.tp.mutator.bcoperator.BCOperatorComparison;
import javassist.CtMethod;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.Mnemonic;

import java.util.ArrayList;
import java.util.List;

public class ComparisonMutator extends Mutator {


    @Override
    public List<Mutant> createListMutant(CtMethod method) {

        CodeIterator iterator = method.getMethodInfo().getCodeAttribute().iterator();
        List<Mutant> mutants = new ArrayList<Mutant>();

        while (iterator.hasNext()) {
            int index = 0;
            try {
                index = iterator.next();
            } catch (BadBytecode badBytecode) {
                badBytecode.printStackTrace();
            }
            int op = iterator.byteAt(index);

            if(BCOperatorComparison.asByteCode(Mnemonic.OPCODE[op])) {
               mutants.add(new Mutant(method, index, this));
            }
        }

        return mutants;
    }

    @Override
    public void doMutate(Mutant mutant) {

        CodeIterator iterator = mutant.getCtMethod().getMethodInfo().getCodeAttribute().iterator();

        int op = iterator.byteAt(mutant.getIndex());

        iterator.writeByte(BCOperatorComparison.valueOf(Mnemonic.OPCODE[op]).replace().getConstant(), mutant.getIndex());

        this.write(mutant.getCtMethod().getDeclaringClass());
    }

    @Override
    public void revert(Mutant mutant) {
        this.doMutate(mutant);
    }
}
