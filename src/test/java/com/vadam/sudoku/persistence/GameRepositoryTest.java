package com.vadam.sudoku.persistence;

import java.nio.file.Files;
import java.nio.file.Path;

import com.vadam.sudoku.model.Difficulty;
import com.vadam.sudoku.model.GameState;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class GameRepositoryTest {
    @Test
    void saveAndLoad() throws Exception {
        Path tmp = Files.createTempFile("sudoku", ".json");
        GameRepository repo = new FileGameRepository();
        GameState s = new GameState(new int[9][9], new boolean[9][9], 12345L, Difficulty.EASY);
        repo.save(tmp, s);
        var loaded = repo.load(tmp);
        assertTrue(loaded.isPresent());
        assertEquals(12345L, loaded.get().getElapsedMillis());
    }
}
