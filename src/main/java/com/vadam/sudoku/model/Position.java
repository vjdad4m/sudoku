package com.vadam.sudoku.model;

import java.util.Objects;

public final class Position {
    private final int row;
    private final int col;

    public Position(int row, int col) {
        if (row < 0 || row >= 9 || col < 0 || col >= 9) {
            throw new IllegalArgumentException("Row or column must be in [0,8]");
        }

        this.row = row;
        this.col = col;
    }

    public int row() {
        return this.row;
    }

    public int col() {
        return this.col;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
