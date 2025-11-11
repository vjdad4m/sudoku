package com.vadam.sudoku.exception;

public class SudokuException extends RuntimeException {
    public SudokuException() {
    }

    public SudokuException(String m) {
        super(m);
    }

    public SudokuException(String m, Throwable t) {
        super(m, t);
    }
}
