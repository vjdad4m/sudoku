package com.vadam.sudoku.exception;

public class PersistenceException extends SudokuException {
    public PersistenceException(String m, Throwable t) {
        super(m, t);
    }
}
