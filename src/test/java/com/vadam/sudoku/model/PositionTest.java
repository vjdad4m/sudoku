package com.vadam.sudoku.model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {
    @Test
    void equalityAndBounds() {
        Position a = new Position(1, 2);
        Position b = new Position(1, 2);
        Position c = new Position(4, 5);
        assertEquals(a, b);
        assertNotEquals(a, c);
        assertThrows(IllegalArgumentException.class, () -> new Position(9, 0));
    }
}
