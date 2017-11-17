package com.istic.tp.editor;

import com.istic.tp.mutant.Mutant;
import com.istic.tp.target.ProjectTarget;
import com.istic.tp.editor.bcoperator.BCOperatorArith;
import javassist.CtMethod;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.Mnemonic;


public class EditorArithOperator extends AbstractEditor {


    public EditorArithOperator(ProjectTarget target) {
        super(target);
    }

    @Override
    protected void createListMutant(final CtMethod method) {

        CodeIterator ci = method.getMethodInfo().getCodeAttribute().iterator();
        while (ci.hasNext()) {

            int index = 0;
            try {

                index = ci.next();

            } catch (BadBytecode badBytecode) {
                badBytecode.printStackTrace();
            }
            int op = ci.byteAt(index);

            if(BCOperatorArith.asByteCode(Mnemonic.OPCODE[op])){
               // System.out.println("MUTANT antoine : "+method.getName()+":"+index);
                this.mutants.add(new Mutant(method, index));
            }
        }
    }

    @Override
    protected void replace(Mutant mutant) {
        System.out.println("replace");
        CodeIterator iterator = mutant.getCtMethod().getMethodInfo().getCodeAttribute().iterator();
        int op = iterator.byteAt(mutant.getIndex());
        System.out.println("new "+BCOperatorArith.valueOf(Mnemonic.OPCODE[op]).replace().getConstant());
        iterator.writeByte(BCOperatorArith.valueOf(Mnemonic.OPCODE[op]).replace().getConstant(), mutant.getIndex());
        this.write(mutant.getCtMethod().getDeclaringClass());
    }


}
