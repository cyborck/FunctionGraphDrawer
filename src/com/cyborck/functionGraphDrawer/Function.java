package com.cyborck.functionGraphDrawer;

import com.cyborck.functionGraphDrawer.functionSystem.Value;
import com.cyborck.functionGraphDrawer.functionSystem.Variable;

public class Function {
    private final Value value;
    private final Variable variable;

    public Function ( Value value, Variable variable ) {
        this.value = value;
        this.variable = variable;
    }

    public double get ( double x ) {
        variable.setValue( x );
        return value.getValue();
    }

    public FunctionValue[] getFunctionValues ( double fromX, double toX, double increment ) {
        FunctionValue[] functionValues = new FunctionValue[ ( int ) ( ( toX - fromX ) / increment ) ];

        for ( int i = 0; i < functionValues.length; i++ ) {
            double x = fromX + increment * i;
            functionValues[ i ] = new FunctionValue( x, get( x ) );
        }

        return functionValues;
    }
}
