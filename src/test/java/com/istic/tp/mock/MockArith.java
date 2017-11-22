package com.istic.tp.mock;

import org.junit.Test;

public class MockArith {


    public int mulInt (int x,int y){

        return x*y;
    }

    public int divInt (int x,int y){

        return x/y;
    }

    public int subInt (int x,int y){

        return x-y;
    }

    //int
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
