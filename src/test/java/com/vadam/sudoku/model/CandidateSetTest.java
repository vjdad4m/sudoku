package com.vadam.sudoku.model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CandidateSetTest {
    @Test
    void addRemoveToggle() {
        CandidateSet cs = new CandidateSet();
        assertTrue(cs.isEmpty());
        cs.add(5);
        assertTrue(cs.has(5));
        cs.toggle(5);
        assertFalse(cs.has(5));
        cs.add(1);
        cs.add(9);
        assertEquals(2, cs.size());
        int raw = cs.raw();
        CandidateSet cs2 = new CandidateSet(raw);
        assertTrue(cs2.has(1) && cs2.has(9));
        cs2.clear();
        assertTrue(cs2.isEmpty());
    }
}
