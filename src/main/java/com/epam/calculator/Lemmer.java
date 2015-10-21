package com.epam.calculator;

import java.util.ArrayList;

/**
 * Created by Леонид on 15.10.2015.
 */
public class Lemmer {
    private final int lengthOfFunctionsName = 3;
    private int curIndex;

    private ArrayList<Token> tokens;

    private Lemmer() {
    }

    private Token parseFunction(final String expression) {
        Token ans;

        switch (expression.substring(curIndex, curIndex + lengthOfFunctionsName)) {
            case "sin":
                ans = new Token(Token.Type.SIN);
                curIndex += lengthOfFunctionsName;
                break;
            case "cos":
                ans = new Token(Token.Type.COS);
                curIndex += lengthOfFunctionsName;
                break;
            case "abs":
                ans = new Token(Token.Type.ABS);
                curIndex += lengthOfFunctionsName;
                break;
            default:
                ans = new Token(Token.Type.UNDEFINED);
        }
        return ans;
    }

    private Token parseNumber(final String expression) {
        int i = curIndex;

        while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
            i++;
        }

        double answer = Double.parseDouble(expression.substring(curIndex, i));

        int j = i;

        if (j < expression.length() && expression.charAt(j) == 'e') {
            j++;

            if (j < expression.length() && expression.charAt(j) == '-' || expression.charAt(j) == '+') {
                j++;
            }


            while (j < expression.length() && Character.isDigit(expression.charAt(j))) {
                j++;
            }

            answer = Double.parseDouble(expression.substring(curIndex, j));
        }

        curIndex = j - 1;

        return new Token(answer);
    }

    public Lemmer(final String expression3) {
        String expression2 = expression3.toLowerCase();
        String expression = expression2.replaceAll("[\\s]+", " ");
        tokens = new ArrayList<>();
        for (curIndex = 0; curIndex < expression.length(); curIndex++) {
            Token tmp;
            switch (expression.charAt(curIndex)) {
                case '+':
                    tmp = new Token(Token.Type.PLUS);
                    break;
                case '-':
                    tmp = new Token(Token.Type.MINUS);
                    break;
                case '*':
                    tmp = new Token(Token.Type.MULTIPLY);
                    break;
                case '/':
                    tmp = new Token(Token.Type.DIVISION);
                    break;
                case '^':
                    tmp = new Token(Token.Type.POWER);
                    break;
                case '(':
                    tmp = new Token(Token.Type.OPEN_BRACKET);
                    break;
                case ')':
                    tmp = new Token(Token.Type.CLOSE_BRACKET);
                    break;
                default:
                    int saveInd = curIndex;
                    if (Character.isDigit(expression.charAt(curIndex)) || expression.charAt(curIndex) == '.') {
                        tmp = parseNumber(expression);
                    } else if (curIndex + lengthOfFunctionsName < expression.length()) {
                        curIndex = saveInd;
                        tmp = parseFunction(expression);
                    } else { // never need in right expression
                        tmp = new Token(Token.Type.UNDEFINED);
                    }
                    break;
            }

            // Maybe, sometime, it will be needed to check wrong expression
            if (tmp.getType() != Token.Type.UNDEFINED) {
                tokens.add(tmp);
            }
        }
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }
}
