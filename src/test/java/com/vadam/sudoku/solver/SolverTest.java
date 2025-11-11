package com.vadam.sudoku.solver;

import com.vadam.sudoku.model.Board;
import com.vadam.sudoku.model.Position;
import com.vadam.sudoku.solver.strategy.SingleCandidateStrategy;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SolverTest {
    @Test
    void singleCandidateFindsStep() {
        Board b = new Board();
        for (int c = 0; c < 8; c++) {
            b.setValue(0, c, c + 1);
        }
        Solver s = new Solver(java.util.List.of(new SingleCandidateStrategy()));
        Optional<Step> step = s.nextStep(b);
        assertTrue(step.isPresent());
        assertEquals(9, step.get().getDigit());
        assertEquals(new Position(0, 8), step.get().getPosition());
    }
}
