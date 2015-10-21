package com.epam.calculator;

/**
 * Created by Леонид on 15.10.2015.
 */
class Token {
    enum Type {
        NUMBER,
        PLUS, MINUS,
        MULTIPLY, DIVISION,
        POWER,
        ABS, COS, SIN,
        OPEN_BRACKET, CLOSE_BRACKET,
        UNDEFINED
    }

    private final Type type;
    private final double value;

    private Token() {
        value = Double.NaN;
        type = Type.UNDEFINED;
    }

    public Token(final Type type) {
        value = Double.NaN;
        this.type = type;
    }

    public Token(final double value) {
        this.value = value;
        type = Type.NUMBER;
    }

    public double getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }
}


