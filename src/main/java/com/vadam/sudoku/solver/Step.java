package com.vadam.sudoku.solver;

import com.vadam.sudoku.model.Position;

public class Step {
    public enum Type {
        SINGLE_CANDIDATE,
        SINGLE_POSITION,
        NAKED_PAIR,
        GUESS
    }

    private final Type type;
    private final Position position;
    private final int digit;
    private final String explanation;

    public Step(Type type, Position position, int digit, String explanation) {
        this.type = type;
        this.position = position;
        this.digit = digit;
        this.explanation = explanation;
    }

    public Type getType() {
        return type;
    }

    public Position gePosition() {
        return position;
    }

    public int getDigit() {
        return digit;
    }

    public String getExplanation() {
        return explanation;
    }
}
