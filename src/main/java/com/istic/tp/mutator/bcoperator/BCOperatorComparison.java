package com.istic.tp.mutator.bcoperator;

public enum BCOperatorComparison {

    /**
     * Operator substitutions
     *
     * <	 <=
     * >	 >=
     * <=	 <
     * >=	 >
     *
     */

    if_icmplt(161), // <  : if value1 is less than value2
    iflt(155),      // <  : (for long, float and double only) if value is less than 0
    if_icmpgt(163), // >  : if value1 is greater than value2
    ifgt(157),      // >  : (for long, float and double only) if value is greater than 0
    if_icmple(164), // <= : if value1 is less than or equal to value2
    ifle(158),      // <= : (for long, float and double only) if value is less than or equal to 0
    if_icmpge(162), // >= : if value1 is greater than or equal to value2
    ifge(156);      // >= : (for long, float and double only) if value is greater than or equal to 0

    private int constant;

    BCOperatorComparison(int constant){
        this.constant=constant;
    }

    public BCOperatorComparison replace() {
        return BCOperatorComparison.values()[(this.ordinal()+4)% BCOperatorComparison.values().length];
    }

    public int getConstant() {
        return constant;
    }

    public static boolean asByteCode(String str) {
        for (BCOperatorComparison me : BCOperatorComparison.values()) {
            if (me.name().equalsIgnoreCase(str))
                return true;
        }
        return false;
    }

}
