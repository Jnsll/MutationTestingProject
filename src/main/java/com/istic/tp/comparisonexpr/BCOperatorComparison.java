package com.istic.tp.comparisonexpr;

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
    if_icmpgt(163), // >  : if value1 is greater than value2
    if_icmple(164), // <= : if value1 is less than or equal to value2
    if_icmpge(162); // >= : if value1 is greater than or equal to value2

    private int constant;

    BCOperatorComparison(int constant){
        this.constant=constant;
    }

    public BCOperatorComparison replace() {
        return BCOperatorComparison.values()[(this.ordinal()+2)% BCOperatorComparison.values().length];
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
