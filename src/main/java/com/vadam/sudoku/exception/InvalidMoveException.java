package com.vadam.sudoku.exception;

public class InvalidMoveException extends SudokuException {
    /**
     * A játékos tiltott lépést próbál végrehajtani.
     */
    public InvalidMoveException(String m) {
        super(m);
    }
}
