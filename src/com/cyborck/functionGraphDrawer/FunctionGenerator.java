package com.cyborck.functionGraphDrawer;

import com.cyborck.functionGraphDrawer.functionSystem.Number;
import com.cyborck.functionGraphDrawer.functionSystem.*;

import java.util.ArrayList;
import java.util.List;

public class FunctionGenerator {
    public static Function generateFunction ( String stringFunction ) {
        Variable x = new Variable();
        Value value = generateValue( stringFunction, x );
        return new Function( value, x );
    }

    private static Calculation generateCalculation ( String stringCalculation, Variable x ) {
        stringCalculation = stringCalculation.trim();

        boolean canRemoveBraces = false;
        if ( stringCalculation.toCharArray()[ 0 ] == '('
                && stringCalculation.toCharArray()[ stringCalculation.length() - 1 ] == ')' ) {
            canRemoveBraces = true;
            int openBraces = 0;
            for ( char c: stringCalculation.toCharArray() )
                if ( c == '(' ) openBraces++;
                else if ( c == ')' ) openBraces--;
                else if ( openBraces == 0 ) canRemoveBraces = false;
        }
        if ( canRemoveBraces )
            stringCalculation = stringCalculation.substring( 1, stringCalculation.length() - 1 );

        List<Integer> subtractionIndices = new ArrayList<>();
        List<Integer> multiplicationIndices = new ArrayList<>();
        List<Integer> divisionIndices = new ArrayList<>();
        List<Integer> powerIndices = new ArrayList<>();

        int openBraces = 0;
        for ( int i = 0; i < stringCalculation.toCharArray().length; i++ ) {
            char c = stringCalculation.toCharArray()[ i ];
            if ( c == '(' ) openBraces++;
            else if ( c == ')' ) openBraces--;
            else if ( openBraces == 0 ) {
                switch ( c ) {
                    case '+' -> {
                        String subString1 = stringCalculation.substring( 0, i );
                        String subString2 = stringCalculation.substring( i ).substring( 1 );
                        subString1 = subString1.length() == 0 ? "0" : subString1;
                        return new Addition( generateValue( subString1, x ), generateValue( subString2, x ) );
                    }
                    case '-' -> subtractionIndices.add( i );
                    case '*' -> multiplicationIndices.add( i );
                    case '/' -> divisionIndices.add( i );
                    case '^' -> powerIndices.add( i );
                }
            }
        }
        if ( subtractionIndices.size() > 0 ) {
            String subString1 = stringCalculation.substring( 0, subtractionIndices.get( 0 ) );
            String subString2 = stringCalculation.substring( subtractionIndices.get( 0 ) ).substring( 1 );
            subString1 = subString1.length() == 0 ? "0" : subString1;
            return new Subtraction( generateValue( subString1, x ), generateValue( subString2, x ) );
        }
        if ( multiplicationIndices.size() > 0 ) {
            String subString1 = stringCalculation.substring( 0, multiplicationIndices.get( 0 ) );
            String subString2 = stringCalculation.substring( multiplicationIndices.get( 0 ) ).substring( 1 );
            return new Multiplication( generateValue( subString1, x ), generateValue( subString2, x ) );
        }
        if ( divisionIndices.size() > 0 ) {
            String subString1 = stringCalculation.substring( 0, divisionIndices.get( 0 ) );
            String subString2 = stringCalculation.substring( divisionIndices.get( 0 ) ).substring( 1 );
            return new Division( generateValue( subString1, x ), generateValue( subString2, x ) );
        }
        if ( powerIndices.size() > 0 ) {
            String subString1 = stringCalculation.substring( 0, powerIndices.get( 0 ) );
            String subString2 = stringCalculation.substring( powerIndices.get( 0 ) ).substring( 1 );
            return new Power( generateValue( subString1, x ), generateValue( subString2, x ) );
        }

        throw new IllegalArgumentException( "Illegal Argument: " + stringCalculation );
    }

    private static Value generateValue ( String stringValue, Variable x ) {
        stringValue = stringValue.trim();

        if ( stringValue.equals( "x" ) ) return x;

        try {
            return new Number( Double.parseDouble( stringValue ) );
        } catch ( NumberFormatException ignored ) {
        }

        return generateCalculation( stringValue, x );
    }
}
