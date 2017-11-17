package com.istic.tp.operatorexpr;

public enum BCOperatorArith {


    iadd(96),
    ladd(97),
    fadd(98),
    dadd(99),

    imul(104),
    lmul(105),
    fmul(106),
    dmul(107),

    isub(100),
    lsub(101),
    fsub(102),
    dsub(103),

    idiv(108),
    ldiv(109),
    fdiv(110),
    ddiv(111);




    private int constant;

    BCOperatorArith(int constant){
        this.constant=constant;
    }

    public BCOperatorArith replace(){
        return BCOperatorArith.values()[(this.ordinal()+8)% BCOperatorArith.values().length];
    }

    public int getConstant() {
        return constant;
    }

    public static boolean asByteCode(String str) {
        for (BCOperatorArith me : BCOperatorArith.values()) {
            if (me.name().equalsIgnoreCase(str))
                return true;
        }
        return false;
    }


}
