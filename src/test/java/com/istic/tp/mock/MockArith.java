package com.istic.tp.mock;

import org.junit.Test;

public class MockArith {

    // OBJECT
    //int
    public Integer mulIntObject (Integer x,Integer y){

        return x*y;
    }

    public Integer divIntObject (Integer x,Integer y){

        return x/y;
    }

    public Integer subIntObject (Integer x,Integer y){

        return x-y;
    }


    public Integer addIntObject (Integer x,Integer y){

        return x+y;
    }


    //long
    public Long addLongObject (Long x,Long y){

        return x+y;
    }

    public Long mulLongObject (Long x,Long y){

        return x*y;
    }

    public Long divLongObject (Long x,Long y){

        return x/y;
    }

    public Long subLongObject (Long x,Long y){

        return x-y;
    }

    //float
    public Float addFloatObject (Float x,Float y){

        return x+y;
    }

    public Float mulFloatObject (Float x,Float y){

        return x*y;
    }

    public Float divFloatObject (Float x,Float y){

        return x/y;
    }

    public Float subFloatObject (Float x,Float y){

        return x-y;
    }

    //Double
    public Double addDoubleObject (Double x,Double y){

        return x+y;
    }

    public Double mulDoubleObject (Double x,Double y){

        return x*y;
    }

    public Double divDoubleObject (Double x,Double y){

        return x/y;
    }

    public Double subDoubleObject (Double x,Double y){

        return x-y;
    }


    // PRIMARY
    //int
    public int mulInt (int x,int y){

        return x*y;
    }

    public int divInt (int x,int y){

        return x/y;
    }

    public int subInt (int x,int y){

        return x-y;
    }

    public int addInt (int x,int y){

        return x+y;
    }


    //long
    public long addLong (long x,long y){

        return x+y;
    }

    public long mulLong (long x,long y){

        return x*y;
    }

    public long divLong (long x,long y){

        return x/y;
    }

    public long subLong (long x,long y){

        return x-y;
    }

    //float
    public float addFloat (float x,float y){

        return x+y;
    }

    public float mulFloat (float x,float y){

        return x*y;
    }

    public float divFloat (float x,float y){

        return x/y;
    }

    public float subFloat (float x,float y){

        return x-y;
    }
    //Double
    public double addDouble (double x,double y){

        return x+y;
    }

    public double mulDouble (double x,double y){

        return x*y;
    }

    public double divDouble (double x,double y){

        return x/y;
    }

    public double subDouble (double x,double y){

        return x-y;
    }



    public int fibo(int n) {
        int result = 0;

        if (n <= 1) {
            result = n;
        } else {
            result = fibo(n - 1) + fibo(n - 2);
        }

        return result;
    }




}
