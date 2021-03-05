package com.cyborck.functionGraphDrawer.functionSystem;

public class Addition extends Calculation {
    public Addition ( Value v1, Value v2 ) {
        super( v1, v2 );
    }

    @Override
    public double calculateValue () {
        return v1.getValue() + v2.getValue();
    }
}
