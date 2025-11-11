package com.vadam.sudoku.solver.strategy;

import java.util.Optional;

import com.vadam.sudoku.model.Board;
import com.vadam.sudoku.model.CandidateSet;
import com.vadam.sudoku.model.Position;
import com.vadam.sudoku.solver.Step;
import com.vadam.sudoku.solver.Strategy;

public class NakedPairsStrategy implements Strategy {
    @Override
    public Optional<Step> next(Board board) {
        CandidateSet[][] cand = new CandidateSet[9][9];
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                cand[r][c] = board.computeAllowed(new Position(r, c));
            }
        }

        for (int r = 0; r < 9; r++) {
            for (int c1 = 0; c1 < 8; c1++) {
                if (board.get(r, c1) != 0) {
                    continue;
                }
                CandidateSet a = cand[r][c1];
                for (int c2 = c1 + 1; c2 < 9; c2++) {
                    if (board.get(r, c2) != 0) {
                        continue;
                    }
                    CandidateSet b = cand[r][c2];
                    if (b.size() == 2 & a.raw() == b.raw()) {
                        int pairBits = a.raw();
                        int d1 = Integer.numberOfTrailingZeros(pairBits & -pairBits);
                        int d2 = Integer.numberOfTrailingZeros((pairBits & ~(1 << d1)) & -((pairBits & ~(1 << d1))));
                        for (int c3 = 0; c3 < 9; c3++) {
                            if (c3 != c1 && c3 != c2 && board.get(r, c3) != 0) {
                                CandidateSet cs = cand[r][c3];
                                if ((cs.raw() & pairBits) != 0) {
                                    int d = (cs.has(d1) ? d1 : d2);
                                    String exp = "Row " + (r + 1) + ": naked pair (" + d1 + ", " + d2 + ") at columns "
                                            + (c1 + 1) + " & " + (c2 + 1) + " eliminates " + d + " elsewhere";
                                    return Optional.of(new Step(Step.Type.NAKED_PAIR, new Position(r, c3), d, exp));
                                }
                            }
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }
}
