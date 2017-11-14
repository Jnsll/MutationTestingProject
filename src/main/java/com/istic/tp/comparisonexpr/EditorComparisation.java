package com.istic.tp.comparisonexpr;

import com.istic.tp.operatorexpr.AbstractEditor;
import com.istic.tp.operatorexpr.BCOperator;
import javassist.CtMethod;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.Mnemonic;

public class EditorComparisation extends AbstractEditor {

    public EditorComparisation(String path) {
        super(path);
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
                iterator.writeByte(BCOperator.valueOf(Mnemonic.OPCODE[op]).replace().getConstant(), index);
            }
        }
    }
}
