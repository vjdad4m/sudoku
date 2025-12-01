package com.vadam.sudoku.model;

import java.util.Arrays;

import com.vadam.sudoku.util.ValidationUtils;

public class Board {
    private final int[][] values = new int[9][9];
    private final boolean[][] fixed = new boolean[9][9];
    private final CandidateSet[][] notes = new CandidateSet[9][9];

    /**
     * Üres táblát hoz létre minden cellában 0 értékkel és üres jegyzetekkel.
     */
    public Board() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                notes[r][c] = new CandidateSet();
            }
        }
    }

    /**
     * Mély másolatot készít egy másik tábla értékeiről, fix jelöléséről és jegyzeteiről.
     */
    public Board(Board other) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                this.values[r][c] = other.values[r][c];
                this.fixed[r][c] = other.fixed[r][c];
                this.notes[r][c] = new CandidateSet(other.notes[r][c].raw());
            }
        }
    }

    /**
     * Visszaadja a megadott sor-oszlop cella értékét.
     */
    public int get(int r, int c) {
        return values[r][c];
    }

    /**
     * Visszaadja a pozíció alapján a cella értékét.
     */
    public int get(Position p) {
        return values[p.row()][p.col()];
    }

    /**
     * Megmondja, hogy a cella fix-e.
     */
    public boolean isFixed(int r, int c) {
        return fixed[r][c];
    }

    /**
     * Megmondja, hogy a megadott pozíció fix-e.
     */
    public boolean isFixed(Position p) {
        return fixed[p.row()][p.col()];
    }

    /**
     * Fix értéket állít be a cellában és törli a kapcsolódó jegyzeteket.
     */
    public void setFixed(int r, int c, int v) {
        values[r][c] = v;
        fixed[r][c] = true;
        notes[r][c].clear();
    }

    /**
     * Eltávolítja a fix jelöléseket az összes celláról.
     */
    public void clearFixed() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                fixed[r][c] = false;
            }
        }
    }

    /**
     * Beállít egy értéket, ha a cella nem fix, és nem nulla érték esetén törli a jegyzeteket.
     */
    public void setValue(int r, int c, int v) {
        if (fixed[r][c]) {
            return;
        }
        values[r][c] = v;
        if (v != 0) {
            notes[r][c].clear();
        }
    }

    /**
     * Pozíció alapján állít be értéket egy cellába.
     */
    public void setValue(Position p, int v) {
        setValue(p.row(), p.col(), v);
    }

    /**
     * Törli egy cella értékét, ha az nem fix.
     */
    public void clearValue(int r, int c) {
        if (!fixed[r][c]) {
            values[r][c] = 0;
        }
    }

    /**
     * Pozíció alapján törli a cella értékét, ha lehet.
     */
    public void clearValue(Position p) {
        clearValue(p.row(), p.col());
    }

    /**
     * Visszaadja a cellához tartozó jelöltkészletet.
     */
    public CandidateSet notes(int r, int c) {
        return notes[r][c];
    }

    /**
     * Visszaadja a pozícióhoz tartozó jelöltkészletet.
     */
    public CandidateSet notes(Position p) {
        return notes[p.row()][p.col()];
    }

    /**
     * Eldönti, hogy a megadott szám ütközik-e a sor, oszlop vagy blokk jelenlegi értékeivel.
     */
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

    /**
     * Megvizsgálja, hogy a tábla jelenlegi állapota tartalmaz-e ellentmondást.
     */
    public boolean isValidSoFar() {
        return ValidationUtils.isValid(values);
    }

    /**
     * Jelzi, hogy a tábla minden cellája kitöltött-e és érvényes-e.
     */
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

    /**
     * Kiszámítja az adott üres cellába helyezhető számok halmazát.
     */
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

    /**
     * Másolatot készít a tábla értékeit tartalmazó tömbről.
     */
    public int[][] values() {
        int[][] copy = new int[9][9];
        for (int r = 0; r < 9; r++) {
            copy[r] = Arrays.copyOf(values[r], 9);
        }
        return copy;
    }

    /**
     * Másolatot készít a fix cellákat jelölő logikai tömbről.
     */
    public boolean[][] fixed() {
        boolean[][] copy = new boolean[9][9];
        for (int r = 0; r < 9; r++) {
            copy[r] = Arrays.copyOf(fixed[r], 9);
        }
        return copy;
    }

    /**
     * Átveszi egy másik tábla értékeit és fix jelölését, majd törli a jegyzeteket.
     */
    public void setAll(int[][] vals, boolean[][] fx) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                values[r][c] = vals[r][c];
                fixed[r][c] = fx[r][c];
                notes[r][c].clear();
            }
        }
    }

    /**
     * Megszámolja, hány üres cella van a táblán.
     */
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
