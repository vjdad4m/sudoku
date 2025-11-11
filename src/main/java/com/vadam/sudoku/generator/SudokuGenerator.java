package com.vadam.sudoku.generator;

import com.vadam.sudoku.model.Board;
import com.vadam.sudoku.model.Difficulty;
import com.vadam.sudoku.model.Position;
import com.vadam.sudoku.solver.Solver;
import com.vadam.sudoku.solver.strategy.NakedPairsStrategy;
import com.vadam.sudoku.solver.strategy.SingleCandidateStrategy;
import com.vadam.sudoku.solver.strategy.SinglePositionStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SudokuGenerator {
    private final Random rng = ThreadLocalRandom.current();
    private final Solver uniquenessSolver = new Solver(
            List.of(new SingleCandidateStrategy(), new SinglePositionStrategy(), new NakedPairsStrategy()));

    public Board generate(Difficulty difficulty) {
        Board solved = generateSolved();
        Board puzzle = new Board(solved);
        int targetClues = targetClues(difficulty);

        List<Position> cells = new ArrayList<>();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                cells.add(new Position(r, c));
            }
        }
        Collections.shuffle(cells, rng);

        for (Position p : cells) {
            int v = puzzle.get(p);
            puzzle.setValue(p, 0);
            Board copy = new Board(puzzle);
            int count = uniquenessSolver.countSolutions(copy, 2);
            if (count != 1 || 81 - puzzle.countEmpty() < targetClues) {
                puzzle.setValue(p, v);
            }
        }

        Board result = new Board();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int v = puzzle.get(r, c);
                if (v != 0) {
                    result.setFixed(r, c, v);
                }
            }
        }
        return result;
    }

    private Board generateSolved() {
        Board b = new Board();
        fill(b, 0, 0);
        return b;
    }

    private boolean fill(Board b, int r, int c) {
        if (r == 9) {
            return true;
        }
        int nr = c == 8 ? r + 1 : r;
        int nc = (c + 1) % 9;
        List<Integer> digits = new ArrayList<>();
        for (int d = 1; d <= 9; d++) {
            digits.add(d);
        }
        Collections.shuffle(digits, rng);
        for (int d : digits) {
            if (!b.conflicts(new com.vadam.sudoku.model.Position(r, c), d)) {
                b.setValue(r, c, d);
                if (fill(b, nr, nc)) {
                    return true;
                }
                b.setValue(r, c, 0);
            }
        }
        return false;
    }

    private int targetClues(Difficulty d) {
        switch (d) {
            case BEGINNER:
                return 45;
            case EASY:
                return 38;
            case MEDIUM:
                return 33;
            case HARD:
                return 29;
            case EXPERT:
                return 26;
            default:
                return 36;
        }
    }
}
