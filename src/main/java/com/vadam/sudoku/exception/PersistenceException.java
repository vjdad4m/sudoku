package com.vadam.sudoku.exception;

public class PersistenceException extends SudokuException {
    /**
     * Adatperzisztencia során fellépő hiba.
     */
    public PersistenceException(String m, Throwable t) {
        super(m, t);
    }
}
