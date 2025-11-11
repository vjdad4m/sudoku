package com.vadam.sudoku.service;

import com.vadam.sudoku.config.Settings;
import com.vadam.sudoku.model.*;
import com.vadam.sudoku.model.event.EventBus;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {
    @Test
    void undoRedoWorks() {
        GameService gs = new GameService(new EventBus(), new Settings());
        Board b = new Board();
        b.setFixed(0, 0, 1);
        gs.newGame(b, Difficulty.EASY);
        Position p = new Position(0, 1);
        gs.setValue(p, 5);
        gs.undo();
        assertEquals(0, gs.board().get(p));
        gs.redo();
        assertEquals(5, gs.board().get(p));
    }
}
