package com.cyborck.functionGraphDrawer.functionSystem;

public class Power extends Calculation {
    public Power ( Value v1, Value v2 ) {
        super( v1, v2 );
    }

    @Override
    public double calculateValue () {
        return Math.pow( v1.getValue(), v2.getValue() );
    }
}
