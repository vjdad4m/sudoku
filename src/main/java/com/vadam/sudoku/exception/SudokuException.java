package com.vadam.sudoku.exception;

public class SudokuException extends RuntimeException {
    /**
     * Általános, üzenet nélküli sudoku kivétel.
     */
    public SudokuException() {
    }

    /**
     * Üzenetet tároló általános sudoku kivétel.
     */
    public SudokuException(String m) {
        super(m);
    }

    /**
     * Üzenettel és okkal rendelkező általános sudoku kivétel.
     */
    public SudokuException(String m, Throwable t) {
        super(m, t);
    }
}
