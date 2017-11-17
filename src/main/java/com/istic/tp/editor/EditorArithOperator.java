package com.istic.tp.editor;

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
    protected void replace(final CtMethod method) {

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
                System.out.println("MUTANT antoine : "+method.getName()+":"+index);
                ci.writeByte(BCOperatorArith.valueOf(Mnemonic.OPCODE[op]).replace().getConstant(),index);
                this.write(method.getDeclaringClass()); // on enregistre les modif
                this.target.launchTest(); // on lance les tests
                this.revert(method); // on remet a l'etat initial


            }
        }




    }




}
