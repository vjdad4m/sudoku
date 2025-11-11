package com.vadam.sudoku.generator;

import com.vadam.sudoku.model.Board;
import com.vadam.sudoku.model.Difficulty;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

public class SudokuGeneratorTest {
    @Test
    void generatesSolvableBeginnerPuzzle() {
        SudokuGenerator gen = new SudokuGenerator();
        Board puzzle = gen.generate(Difficulty.BEGINNER);
        assertTrue(puzzle.countEmpty() > 0);
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int v = puzzle.get(r, c);
                if (v != 0) {
                    int old = puzzle.get(r, c);
                    puzzle.setValue(r, c, 0);
                    assertThat(puzzle.isValidSoFar()).isTrue();
                    puzzle.setValue(r, c, old);
                }
            }
        }
    }

    @Test
    void generatesSolvableEasyPuzzle() {
        SudokuGenerator gen = new SudokuGenerator();
        Board puzzle = gen.generate(Difficulty.EASY);
        assertTrue(puzzle.countEmpty() > 0);
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int v = puzzle.get(r, c);
                if (v != 0) {
                    int old = puzzle.get(r, c);
                    puzzle.setValue(r, c, 0);
                    assertThat(puzzle.isValidSoFar()).isTrue();
                    puzzle.setValue(r, c, old);
                }
            }
        }
    }

    @Test
    void generatesSolvableMediumPuzzle() {
        SudokuGenerator gen = new SudokuGenerator();
        Board puzzle = gen.generate(Difficulty.MEDIUM);
        assertTrue(puzzle.countEmpty() > 0);
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int v = puzzle.get(r, c);
                if (v != 0) {
                    int old = puzzle.get(r, c);
                    puzzle.setValue(r, c, 0);
                    assertThat(puzzle.isValidSoFar()).isTrue();
                    puzzle.setValue(r, c, old);
                }
            }
        }
    }

    @Test
    void generatesSolvableHardPuzzle() {
        SudokuGenerator gen = new SudokuGenerator();
        Board puzzle = gen.generate(Difficulty.HARD);
        assertTrue(puzzle.countEmpty() > 0);
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int v = puzzle.get(r, c);
                if (v != 0) {
                    int old = puzzle.get(r, c);
                    puzzle.setValue(r, c, 0);
                    assertThat(puzzle.isValidSoFar()).isTrue();
                    puzzle.setValue(r, c, old);
                }
            }
        }
    }

    @Test
    void generatesSolvableExpertPuzzle() {
        SudokuGenerator gen = new SudokuGenerator();
        Board puzzle = gen.generate(Difficulty.EXPERT);
        assertTrue(puzzle.countEmpty() > 0);
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int v = puzzle.get(r, c);
                if (v != 0) {
                    int old = puzzle.get(r, c);
                    puzzle.setValue(r, c, 0);
                    assertThat(puzzle.isValidSoFar()).isTrue();
                    puzzle.setValue(r, c, old);
                }
            }
        }
    }
}
