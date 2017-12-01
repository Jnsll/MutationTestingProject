package com.istic.tp.mutator;

import com.istic.tp.mutant.Mutant;
import com.istic.tp.mutator.bcoperator.BCOperatorArith;
import com.istic.tp.mutator.bcoperator.BCOperatorComparison;
import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.Mnemonic;

import java.util.ArrayList;
import java.util.List;


public class ArithMutator extends Mutator {


    @Override
    public List<Mutant> createListMutant(final CtMethod method) {
        CtMethod copy = null;
        try {

            copy = CtNewMethod.copy(method,method.getDeclaringClass(),null);
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

        try {
            method.setBody(copy,null);
            this.write(method.getDeclaringClass());
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
        CodeIterator ci = method.getMethodInfo().getCodeAttribute().iterator();
        List<Mutant> mutants = new ArrayList<Mutant>();
        while (ci.hasNext()) {

            int index = 0;
            try {

                index = ci.next();

            } catch (BadBytecode badBytecode) {
                badBytecode.printStackTrace();
            }
            int op = ci.byteAt(index);
            //System.out.println(index+": "+ Mnemonic.OPCODE[op]);
            if(BCOperatorArith.asByteCode(Mnemonic.OPCODE[op])){
               // System.out.println("MUTANT antoine : "+method.getName()+":"+index);
                mutants.add(new Mutant(method, index, this));
            }
        }

        return mutants;
    }

    @Override
    public void doMutate(Mutant mutant) {

        CodeIterator iterator = mutant.getCtMethod().getMethodInfo().getCodeAttribute().iterator();

        int op = iterator.byteAt(mutant.getIndex());

        iterator.writeByte(BCOperatorArith.valueOf(Mnemonic.OPCODE[op]).replace().getConstant(), mutant.getIndex());

        this.write(mutant.getCtMethod().getDeclaringClass());
    }


}
