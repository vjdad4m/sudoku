package com.vadam.sudoku.solver.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import com.vadam.sudoku.model.Board;
import com.vadam.sudoku.model.Position;
import com.vadam.sudoku.solver.Step;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class SingleCandidateStrategyTest {
    private final SingleCandidateStrategy strategy = new SingleCandidateStrategy();

    @Test
    void findsStepWhenOnlyOneCandidateFitsCell() {
        Board board = new Board();
        for (int c = 0; c < 8; c++) {
            board.setFixed(0, c, c + 1);
        }

        Optional<Step> step = strategy.next(board);

        assertThat(step).isPresent();
        Step s = step.get();
        assertThat(s.getType()).isEqualTo(Step.Type.SINGLE_CANDIDATE);
        assertThat(s.getDigit()).isEqualTo(9);
        assertThat(s.getPosition()).isEqualTo(new Position(0, 8));
        assertThat(s.getExplanation()).contains("Only one candidate fits");
    }

    @Test
    void returnsEmptyWhenAllCellsHaveMultipleCandidates() {
        Board board = new Board();

        assertThat(strategy.next(board)).isEmpty();
    }
}
