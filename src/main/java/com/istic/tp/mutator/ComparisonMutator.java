package com.istic.tp.mutator;

import com.istic.tp.mutant.Mutant;
import com.istic.tp.mutator.bcoperator.BCOperatorArith;
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
        // System.out.println("method: " + method.getName());
        while (iterator.hasNext()) {
            int index = 0;
            try {
                index = iterator.next();
            } catch (BadBytecode badBytecode) {
                badBytecode.printStackTrace();
            }
            int op = iterator.byteAt(index);
            // System.out.println("index: " + index + "-  op: " + op);
            if(BCOperatorComparison.asByteCode(Mnemonic.OPCODE[op])) {
                // System.out.println("MUTANT emmanuel: "+method.getName()+":"+index);
                mutants.add(new Mutant(method, index, this));
            }
        }
        return mutants;
    }

    @Override
    public void doMutate(Mutant mutant) {
        CodeIterator iterator = mutant.getCtMethod().getMethodInfo().getCodeAttribute().iterator();
        //  verif a cause de javassist
        if(mutant.getIndex() >= iterator.get().getCodeLength()){
            System.err.println("Bad Mutant : "+ mutant);
            return;
        }
        int op = iterator.byteAt(mutant.getIndex());
        if(op >= Mnemonic.OPCODE.length){
            System.err.println("Bad Mutant : "+ mutant);
            return;
        }
        if(!BCOperatorComparison.asByteCode(Mnemonic.OPCODE[op])){
            System.err.println("Bad Mutant : "+ mutant);
            return;
        }
        //
        iterator.writeByte(BCOperatorComparison.valueOf(Mnemonic.OPCODE[op]).replace().getConstant(), mutant.getIndex());
        this.write(mutant.getCtMethod().getDeclaringClass());
    }
}
