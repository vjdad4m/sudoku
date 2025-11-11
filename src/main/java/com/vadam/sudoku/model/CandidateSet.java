package com.vadam.sudoku.model;

public class CandidateSet {
    private int bits;

    public CandidateSet() {
        this.bits = 0;
    }

    public CandidateSet(int bits) {
        this.bits = bits & 0x3FE;
    }

    public boolean has(int d) {
        return (d >= 1 && d <= 9 && (bits & (1 << d)) != 0);
    }

    public void add(int d) {
        if (d >= 1 && d <= 9) {
            bits |= (1 << d);
        }
    }

    public void remove(int d) {
        if (d >= 1 && d <= 9) {
            bits &= ~(1 << d);
        }
    }

    public void toggle(int d) {
        if (d >= 1 && d <= 9) {
            bits ^= (1 << d);
        }
    }

    public void clear() {
        bits = 0;
    }

    public int size() {
        int c = 0;
        int b = bits;
        while (b != 0) {
            b &= b - 1;
            c++;
        }
        return c;
    }

    public int raw() {
        return bits;
    }

    public void setRaw(int newBits) {
        bits = newBits & 0x3FE;
    }

    public boolean isEmpty() {
        return (bits == 0);
    }
}
