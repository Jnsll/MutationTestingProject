package com.istic.tp.mock;

public class VoidMethodMock {

    int count;

    public void incrementsCount() {
        this.count++;
    }


    public Void incrementsCountMaj() {
        this.count++;
        return null;
    }

    public Boolean methodBoolean() {
        return true;
    }
}

