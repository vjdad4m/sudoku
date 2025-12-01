package com.vadam.sudoku.exception;

public class PuzzleUnsolvableException extends SudokuException {
    /**
     * Jelzi, hogy a rejtvénynek nincs érvényes megoldása.
     */
    public PuzzleUnsolvableException(String m) {
        super(m);
    }
}
