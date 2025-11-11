
package com.vadam.sudoku.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {
    @Test
    void gettersSetters() {
        GameState s = new GameState();
        s.setValues(new int[9][9]);
        s.setFixed(new boolean[9][9]);
        s.setElapsedMillis(42L);
        s.setDifficulty(Difficulty.EASY);
        assertEquals(42L, s.getElapsedMillis());
        assertEquals(Difficulty.EASY, s.getDifficulty());
        assertNotNull(s.getValues());
        assertNotNull(s.getFixed());
    }
}
