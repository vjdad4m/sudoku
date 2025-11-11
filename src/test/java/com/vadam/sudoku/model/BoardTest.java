package com.vadam.sudoku.model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    void setAndConflicts() {
        Board b = new Board();
        b.setFixed(0, 0, 5);
        b.setValue(0, 1, 5);
        assertTrue(b.conflicts(new Position(0, 1), 5));
        b.setValue(0, 1, 0);
        assertTrue(b.conflicts(new Position(0, 1), 5));
    }

    @Test
    void allowedCandidates() {
        Board b = new Board();
        for (int c = 0; c < 8; c++) {
            b.setFixed(0, c, c + 1);
        }
        CandidateSet cs = b.computeAllowed(new Position(0, 8));
        assertTrue(cs.has(9));
        assertEquals(1, cs.size());
    }

    @Test
    void validPlacementRules() {
        Board b = new Board();
        b.setValue(0, 0, 5);
        b.setValue(0, 1, 3);
        b.setValue(1, 0, 6);
        assertTrue(b.isValidSoFar());
        b.setValue(1, 1, 6);
        assertFalse(b.isValidSoFar());
    }
}
