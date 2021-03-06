package com.example.muszkie.calc;

public enum Sign {
    ADD,
    SUBSTRACT,
    MULTIPLY,
    DIVIDE,
    EQUAL,
    POW,
    DOT;


    @Override
    public String toString(){
        switch (this) {
            case ADD:
                return "+";
            case SUBSTRACT:
                return "-";
            case MULTIPLY:
                return "*";
            case DIVIDE:
                return "/";
            case EQUAL:
                return "=";
            case POW:
                return "^";
            case DOT:
                return ".";
        }
        return null;

    }

    public char toChar(){
        switch (this) {
            case ADD:
                return '+';
            case SUBSTRACT:
                return '-';
            case MULTIPLY:
                return '*';
            case DIVIDE:
                return '/';
            case EQUAL:
                return '=';
            case POW:
                return '^';
            case DOT:
                return '.';
        }
        return '1';
    }

    public String toSign(){
        switch (this) {
            case ADD:
                return " + ";
            case SUBSTRACT:
                return " - ";
            case MULTIPLY:
                return " * ";
            case DIVIDE:
                return " / ";
            case EQUAL:
                return " = ";
            case POW:
                return " ^ ";
            case DOT:
                return " . ";
        }
        return null;
    }

    public String toMinus(){
        switch (this) {
            case SUBSTRACT:
                return " -";
        }
        return null;
    }

    public String toDotSpace(){
        switch (this) {
            case DOT:
                return ". ";
        }
        return null;
    }
}
