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

    /**
     * Logikai lépést ír le a típussal, célpozícióval, beírandó számmal és indoklással.
     */
    public Step(Type type, Position position, int digit, String explanation) {
        this.type = type;
        this.position = position;
        this.digit = digit;
        this.explanation = explanation;
    }

    /**
     * Visszaadja a lépés típusát.
     */
    public Type getType() {
        return type;
    }

    /**
     * Visszaadja a célpozíciót.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Visszaadja a behelyezendő számot.
     */
    public int getDigit() {
        return digit;
    }

    /**
     * Visszaadja a lépés magyarázatát.
     */
    public String getExplanation() {
        return explanation;
    }
}
