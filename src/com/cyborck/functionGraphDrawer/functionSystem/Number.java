package com.cyborck.functionGraphDrawer.functionSystem;

public class Number implements Value {
    private final double number;

    public Number ( double number ) {
        this.number = number;
    }

    @Override
    public double getValue () {
        return number;
    }
}
