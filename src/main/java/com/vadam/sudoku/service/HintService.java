package com.vadam.sudoku.service;

import com.vadam.sudoku.model.Board;
import com.vadam.sudoku.solver.Solver;
import com.vadam.sudoku.solver.Step;
import com.vadam.sudoku.solver.strategy.NakedPairsStrategy;
import com.vadam.sudoku.solver.strategy.SingleCandidateStrategy;
import com.vadam.sudoku.solver.strategy.SinglePositionStrategy;

import java.util.List;
import java.util.Optional;

public class HintService {
    private final Solver solver = new Solver(
            List.of(new SingleCandidateStrategy(), new SinglePositionStrategy(), new NakedPairsStrategy()));

    public Optional<Step> nextStep(Board b) {
        return solver.nextStep(new Board(b));
    }

    public int applyLogical(Board b) {
        return solver.solveLogically(b).size();
    }

    public boolean solveFully(Board b) {
        Board copy = new Board(b);
        solver.solveLogically(b);
        if (copy.isSolved()) {
            b.setAll(copy.values(), copy.fixed());
            return true;
        }
        boolean ok = solver.solveBacktracking(copy);
        if (ok) {
            b.setAll(copy.values(), copy.fixed());
        }
        return ok;
    }
}
