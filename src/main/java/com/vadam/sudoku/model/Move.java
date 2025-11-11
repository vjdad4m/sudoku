package com.vadam.sudoku.model;

public abstract class Move {
    public static final class ValueMove extends Move {
        public final Position pos;
        public final int before, after;

        public ValueMove(Position pos, int before, int after) {
            this.pos = pos;
            this.before = before;
            this.after = after;
        }
    }

    public static final class NoteToggleMove extends Move {
        public final Position pos;
        public final int digit;

        public NoteToggleMove(Position pos, int digit) {
            this.pos = pos;
            this.digit = digit;
        }
    }
}
