package com.vadam.sudoku.model;

import java.util.Arrays;

import com.vadam.sudoku.util.ValidationUtils;

public class Board {
    private final int[][] values = new int[9][9];
    private final boolean[][] fixed = new boolean[9][9];
    private final CandidateSet[][] notes = new CandidateSet[9][9];

    public Board() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                notes[r][c] = new CandidateSet();
            }
        }
    }

    public Board(Board other) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                this.values[r][c] = other.values[r][c];
                this.fixed[r][c] = other.fixed[r][c];
                this.notes[r][c] = new CandidateSet(other.notes[r][c].raw());
            }
        }
    }

    public int get(int r, int c) {
        return values[r][c];
    }

    public int get(Position p) {
        return values[p.row()][p.col()];
    }

    public boolean isFixed(int r, int c) {
        return fixed[r][c];
    }

    public boolean isFixed(Position p) {
        return fixed[p.row()][p.col()];
    }

    public void setFixed(int r, int c, int v) {
        values[r][c] = v;
        fixed[r][c] = true;
        notes[r][c].clear();
    }

    public void clearFixed() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                fixed[r][c] = false;
            }
        }
    }

    public void setValue(int r, int c, int v) {
        if (fixed[r][c]) {
            return;
        }
        values[r][c] = v;
        if (v != 0) {
            notes[r][c].clear();
        }
    }

    public void setValue(Position p, int v) {
        setValue(p.row(), p.col(), v);
    }

    public void clearValue(int r, int c) {
        if (!fixed[r][c]) {
            values[r][c] = 0;
        }
    }

    public void clearValue(Position p) {
        clearValue(p.row(), p.col());
    }

    public CandidateSet notes(int r, int c) {
        return notes[r][c];
    }

    public CandidateSet notes(Position p) {
        return notes[p.row()][p.col()];
    }

    public boolean conflicts(Position p, int v) {
        if (v == 0) {
            return false;
        }
        int r = p.row();
        int c = p.col();
        for (int i = 0; i < 9; i++) {
            if (i != c && values[r][i] == v) {
                return true;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (i != r && values[i][c] == v) {
                return true;
            }
        }
        int br = (r / 3) * 3;
        int bc = (c / 3) * 3;
        for (int rr = br; rr < br + 3; rr++) {
            for (int cc = bc; cc < bc + 3; cc++) {
                if (rr == r && cc == c) {
                    continue;
                }
                if (values[rr][cc] == v) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValidSoFar() {
        return ValidationUtils.isValid(values);
    }

    public boolean isSolved() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (values[r][c] == 0) {
                    return false;
                }
            }
        }
        return ValidationUtils.isValid(values);
    }

    public CandidateSet computeAllowed(Position p) {
        CandidateSet cs = new CandidateSet();
        if (get(p) != 0) {
            return cs;
        }
        boolean[] used = new boolean[10];
        int r = p.row();
        int c = p.col();
        for (int i = 0; i < 9; i++) {
            used[values[r][i]] = true;
        }
        for (int i = 0; i < 9; i++) {
            used[values[i][c]] = true;
        }
        int br = (r / 3) * 3;
        int bc = (c / 3) * 3;
        for (int rr = br; rr < br + 3; rr++) {
            for (int cc = bc; cc < bc + 3; cc++) {
                used[values[rr][cc]] = true;
            }
        }
        for (int d = 1; d <= 9; d++) {
            if (!used[d]) {
                cs.add(d);
            }
        }
        return cs;
    }

    public int[][] values() {
        int[][] copy = new int[9][9];
        for (int r = 0; r < 9; r++) {
            copy[r] = Arrays.copyOf(values[r], 9);
        }
        return copy;
    }

    public boolean[][] fixed() {
        boolean[][] copy = new boolean[9][9];
        for (int r = 0; r < 9; r++) {
            copy[r] = Arrays.copyOf(fixed[r], 9);
        }
        return copy;
    }

    public void setAll(int[][] vals, boolean[][] fx) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                values[r][c] = vals[r][c];
                fixed[r][c] = fx[r][c];
                notes[r][c].clear();
            }
        }
    }

    public int countEmpty() {
        int n = 0;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (values[r][c] == 0) {
                    n++;
                }
            }
        }
        return n;
    }
}
