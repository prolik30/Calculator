package com.epam.calculator;

import java.util.ArrayList;

/**
 * Created by Леонид on 15.10.2015.
 */
public class Parser {

    private int curTokenInd;
    private ArrayList<Token> tokens;

    Parser(final ArrayList<Token> tokens) {
        curTokenInd = 0;
        this.tokens = tokens;
    }

    public double getAns() {

        return expressionCalc();
    }

    private double expressionCalc() {
        double answer = summand();

        while (curTokenInd + 1 < tokens.size()) {
            Token.Type sign = tokens.get(curTokenInd + 1).getType();

            if (!(sign == Token.Type.PLUS || sign == Token.Type.MINUS)) {
                break;
            }

            curTokenInd += 2;

            if (curTokenInd >= tokens.size()) {
                break;
            }

            double tmp = summand();

            if (sign == Token.Type.PLUS) {
                answer += tmp;
            } else if (sign == Token.Type.MINUS) {
                answer -= tmp;
            }

        }

        return answer;
    }

    private double summand() {
        double answer = multiplier();

        while (curTokenInd + 1 < tokens.size()) {
            Token.Type sign = tokens.get(curTokenInd + 1).getType();

            if (!(sign == Token.Type.MULTIPLY || sign == Token.Type.DIVISION)) {
                break;
            }

            curTokenInd += 2;

            if (curTokenInd >= tokens.size()) {
                break;
            }

            double tmp = multiplier();

            if (sign == Token.Type.MULTIPLY) {
                answer *= tmp;
            } else if (sign == Token.Type.DIVISION) {
                answer /= tmp;
            }
        }

        return answer;
    }

    private double multiplier() {
        Token.Type tmpType = tokens.get(curTokenInd).getType();
        double answer = Double.NaN;
        switch (tmpType) {
            case OPEN_BRACKET:
                curTokenInd++;
                answer = expressionCalc();
                curTokenInd++;
                break;
            case MINUS:
                curTokenInd++;
                answer = -multiplier();
                break;
            case PLUS:
                curTokenInd++;
                answer = multiplier();
                break;
            default:
                answer = function();
                break;
        }

        while (curTokenInd + 1 < tokens.size()) {
            Token.Type sign = tokens.get(curTokenInd + 1).getType();

            if (sign != Token.Type.POWER) {
                break;
            }

            curTokenInd += 2;

            if (curTokenInd >= tokens.size()) {
                break;
            }

            double tmp = multiplier();

            answer = Math.pow(answer, tmp);
        }


        return answer;
    }

    private double function() {
        Token.Type tmpType = tokens.get(curTokenInd).getType();
        if (tmpType == Token.Type.ABS || tmpType == Token.Type.COS || tmpType == Token.Type.SIN) {
            curTokenInd++; // open bracket
            double answer = processFunction(tmpType, expressionCalc());
            curTokenInd++; // close bracket
            return answer;
        }

        return tokens.get(curTokenInd).getValue();
    }

    private double processFunction(final Token.Type type, final double arg) {
        switch (type) {
            case ABS:
                return Math.abs(arg);
            case COS:
                return Math.cos(arg);
            case SIN:
                return Math.sin(arg);
            default:
                return Double.NaN;
        }
    }
}

