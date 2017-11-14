package com.istic.tp.operatorexpr;

import com.istic.tp.ProjectTarget;
import javassist.CtMethod;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.Mnemonic;


public class EditorArthOperator extends AbstractEditor {


    public EditorArthOperator(ProjectTarget target) {
        super(target);
    }

    @Override
    protected void replace(final CtMethod ct) {

        CodeIterator ci = ct.getMethodInfo().getCodeAttribute().iterator();
        while (ci.hasNext()) {
            int index = 0;
            try {

                index = ci.next();

            } catch (BadBytecode badBytecode) {
                badBytecode.printStackTrace();
            }
            int op = ci.byteAt(index);
            if(BCOperator.asByteCode(Mnemonic.OPCODE[op])){

                ci.writeByte(BCOperator.valueOf(Mnemonic.OPCODE[op]).replace().getConstant(),index);
                System.out.println(BCOperator.valueOf(Mnemonic.OPCODE[op]).replace().toString()+":"+ct.getName()+":"+index);
                this.target.launchTest();
                this.revert(ct);
            }
        }


    }




}
