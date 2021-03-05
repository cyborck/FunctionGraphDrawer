package com.cyborck.functionGraphDrawer.functionSystem;

public abstract class Calculation implements Value {
    protected final Value v1;
    protected final Value v2;

    protected Calculation ( Value v1, Value v2 ) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public abstract double calculateValue ();

    @Override
    public double getValue () {
        return calculateValue();
    }
}
