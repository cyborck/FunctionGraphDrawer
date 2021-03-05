package com.cyborck.functionGraphDrawer.functionSystem;

public class Subtraction extends Calculation {
    public Subtraction ( Value v1, Value v2 ) {
        super( v1, v2 );
    }

    @Override
    public double calculateValue () {
        return v1.getValue() - v2.getValue();
    }
}
