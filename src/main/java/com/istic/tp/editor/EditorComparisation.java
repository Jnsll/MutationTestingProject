package com.istic.tp.editor;

import com.istic.tp.target.ProjectTarget;
import com.istic.tp.editor.bcoperator.BCOperatorComparison;
import javassist.CtMethod;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.Mnemonic;

public class EditorComparisation extends AbstractEditor {

    public EditorComparisation(ProjectTarget target) {
        super(target);
    }

    @Override
    protected void replace(CtMethod method) {
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

                iterator.writeByte(BCOperatorComparison.valueOf(Mnemonic.OPCODE[op]).replace().getConstant(), index);
                System.out.println("MUTANT emmanuel: "+method.getName()+":"+index);
                this.write(method.getDeclaringClass()); // on enregistre les modif
                this.target.launchTest(); // on lance les tests
                this.revert(method); // on remet a l'etat initial
            }
        }
    }
}
