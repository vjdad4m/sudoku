package com.vadam.sudoku.solver.strategy;

import java.util.Optional;

import com.vadam.sudoku.model.Board;
import com.vadam.sudoku.model.Position;
import com.vadam.sudoku.solver.Step;
import com.vadam.sudoku.solver.Strategy;

public class SinglePositionStrategy implements Strategy {
    /**
     * Megkeresi azt a helyet a sorban, oszlopban vagy blokkban, ahová csak egy adott szám illik.
     */
    @Override
    public Optional<Step> next(Board board) {
        for (int r = 0; r < 9; r++) {
            for (int d = 1; d <= 9; d++) {
                int count = 0, lastC = -1;
                for (int c = 0; c < 9; c++) {
                    if (board.get(r, c) == 0 && !board.conflicts(new Position(r, c), d)) {
                        count++;
                        lastC = c;
                    }
                }
                if (count == 1) {
                    Position p = new Position(r, lastC);
                    String exp = "Row " + (r + 1) + ": digit " + d + " fits only at " + p;
                    return Optional.of(new Step(Step.Type.SINGLE_POSITION, p, d, exp));
                }
            }
        }
        for (int c = 0; c < 9; c++) {
            for (int d = 1; d <= 9; d++) {
                int count = 0, lastR = -1;
                for (int r = 0; r < 9; r++) {
                    if (board.get(r, c) == 0 && !board.conflicts(new Position(r, c), d)) {
                        count++;
                        lastR = r;
                    }
                }
                if (count == 1) {
                    Position p = new Position(lastR, c);
                    String exp = "Column " + (c + 1) + ": digit " + d + " fits only at " + p;
                    return Optional.of(new Step(Step.Type.SINGLE_POSITION, p, d, exp));
                }
            }
        }
        for (int br = 0; br < 9; br += 3)
            for (int bc = 0; bc < 9; bc += 3) {
                for (int d = 1; d <= 9; d++) {
                    int count = 0;
                    Position last = null;
                    for (int r = br; r < br + 3; r++)
                        for (int c = bc; c < bc + 3; c++) {
                            if (board.get(r, c) == 0 && !board.conflicts(new Position(r, c), d)) {
                                count++;
                                last = new Position(r, c);
                            }
                        }
                    if (count == 1) {
                        String exp = "Block (" + ((br / 3) + 1) + "," + ((bc / 3) + 1) + "): digit " + d
                                + " fits only at " + last;
                        return Optional.of(new Step(Step.Type.SINGLE_POSITION, last, d, exp));
                    }
                }
            }
        return Optional.empty();
    }
}
