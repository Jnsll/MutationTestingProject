package com.istic.tp.editor;

import com.istic.tp.mutant.Mutant;
import com.istic.tp.target.ProjectTarget;
import com.istic.tp.editor.bcoperator.BCOperatorComparison;
import javassist.CtMethod;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.Mnemonic;

public class EditorComparison extends AbstractEditor {

    public EditorComparison(ProjectTarget target) {
        super(target);
    }

    @Override
    protected void createListMutant(CtMethod method) {
        CodeIterator iterator = method.getMethodInfo().getCodeAttribute().iterator();
        while (iterator.hasNext()) {
            int index = 0;
            try {
                index = iterator.next();
            } catch (BadBytecode badBytecode) {
                badBytecode.printStackTrace();
            }
            int op = iterator.byteAt(index);

            if(BCOperatorComparison.asByteCode(Mnemonic.OPCODE[op])) {
//                System.out.println("MUTANT emmanuel: "+method.getName()+":"+index);
                this.mutants.add(new Mutant(method, index));
            }
        }
    }

    @Override
    protected void replace(Mutant mutant) {
        CodeIterator iterator = mutant.getCtMethod().getMethodInfo().getCodeAttribute().iterator();
        int op = iterator.byteAt(mutant.getIndex());
        iterator.writeByte(BCOperatorComparison.valueOf(Mnemonic.OPCODE[op]).replace().getConstant(), mutant.getIndex());
        this.write(mutant.getCtMethod().getDeclaringClass());
    }
}
