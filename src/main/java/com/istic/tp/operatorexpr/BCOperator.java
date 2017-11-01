package com.istic.tp.operatorexpr;

public enum BCOperator {


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

    BCOperator(int constant){
        this.constant=constant;
    }

    public BCOperator replace(){
        return BCOperator.values()[(this.ordinal()+8)% BCOperator.values().length];
    }

    public int getConstant() {
        return constant;
    }

    public static boolean asByteCode(String str) {
        for (BCOperator me : BCOperator.values()) {
            if (me.name().equalsIgnoreCase(str))
                return true;
        }
        return false;
    }


}
