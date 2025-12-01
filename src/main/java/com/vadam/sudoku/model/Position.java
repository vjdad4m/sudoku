package com.vadam.sudoku.model;

import java.util.Objects;

public final class Position {
    private final int row;
    private final int col;

    /**
     * Pozíciót hoz létre érvényes sor- és oszlopindexszel.
     */
    public Position(int row, int col) {
        if (row < 0 || row >= 9 || col < 0 || col >= 9) {
            throw new IllegalArgumentException("Row or column must be in [0,8]");
        }

        this.row = row;
        this.col = col;
    }

    /**
     * A pozíció sor indexét adja vissza.
     */
    public int row() {
        return this.row;
    }

    /**
     * A pozíció oszlop indexét adja vissza.
     */
    public int col() {
        return this.col;
    }

    /**
     * Kettő pozíciót hasonlít össze sor és oszlop alapján.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }
        Position p = (Position) o;
        return (this.row == p.row && this.col == p.col);
    }

    /**
     * Hash kódot számít a sor és oszlop értékéből.
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    /**
     * Szövegesen megjeleníti a pozíciót (sor,oszlop) formátumban.
     */
    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
