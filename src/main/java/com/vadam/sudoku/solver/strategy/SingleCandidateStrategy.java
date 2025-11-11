package com.vadam.sudoku.solver.strategy;

import com.vadam.sudoku.model.Board;
import com.vadam.sudoku.model.CandidateSet;
import com.vadam.sudoku.model.Position;
import com.vadam.sudoku.solver.Step;
import com.vadam.sudoku.solver.Strategy;

import java.util.Optional;

public class SingleCandidateStrategy implements Strategy {
    @Override
    public Optional<Step> next(Board board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board.get(r, c) == 0) {
                    Position p = new Position(r, c);
                    CandidateSet cs = board.computeAllowed(p);
                    if (cs.size() == 1) {
                        for (int d = 1; d <= 9; d++) {
                            if (cs.has(d)) {
                                String exp = "Only one candidate fits at " + p + ": " + d;
                                return Optional.of(new Step(Step.Type.SINGLE_CANDIDATE, p, d, exp));
                            }
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }
}
