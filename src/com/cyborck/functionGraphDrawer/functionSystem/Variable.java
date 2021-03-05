package com.cyborck.functionGraphDrawer.functionSystem;

public class Variable implements Value {
    private double value = 0;

    @Override
    public double getValue () {
        return value;
    }

    public void setValue ( double value ) {
        this.value = value;
    }
}
