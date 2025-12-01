package com.vadam.sudoku.model;

public final class CandidateSet {
    private int bits;

    /**
     * Üres jelöltkészletet hoz létre.
     */
    public CandidateSet() {
        this.bits = 0;
    }

    /**
     * A megadott bitmaszk alapján inicializálja a jelöltkészletet.
     */
    public CandidateSet(int bits) {
        this.bits = bits & 0x3FE;
    }

    /**
     * Megadja, hogy a számjegy benne van-e a jelöltkészletben.
     */
    public boolean has(int d) {
        return (d >= 1 && d <= 9 && (bits & (1 << d)) != 0);
    }

    /**
     * Hozzáad egy számot, ha az 1 és 9 között van.
     */
    public void add(int d) {
        if (d >= 1 && d <= 9) {
            bits |= (1 << d);
        }
    }

    /**
     * Eltávolít egy számot, ha az 1 és 9 között van.
     */
    public void remove(int d) {
        if (d >= 1 && d <= 9) {
            bits &= ~(1 << d);
        }
    }

    /**
     * Átkapcsol egy számot a készletben.
     */
    public void toggle(int d) {
        if (d >= 1 && d <= 9) {
            bits ^= (1 << d);
        }
    }

    /**
     * Teljesen kiüríti a jelöltkészletet.
     */
    public void clear() {
        bits = 0;
    }

    /**
     * Megszámolja, hány jelölt szerepel a készletben.
     */
    public int size() {
        int c = 0;
        int b = bits;
        while (b != 0) {
            b &= b - 1;
            c++;
        }
        return c;
    }

    /**
     * Visszaadja a belső bitmaszkot.
     */
    public int raw() {
        return bits;
    }

    /**
     * Beállítja a belső bitmaszkot az érvényes tartományra korlátozva.
     */
    public void setRaw(int newBits) {
        bits = newBits & 0x3FE;
    }

    /**
     * Megadja, hogy a készlet üres-e.
     */
    public boolean isEmpty() {
        return (bits == 0);
    }
}
