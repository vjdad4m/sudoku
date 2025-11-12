package com.vadam.sudoku.solver.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import com.vadam.sudoku.model.Board;
import com.vadam.sudoku.model.Position;
import com.vadam.sudoku.solver.Step;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class NakedPairsStrategyTest {
    private final NakedPairsStrategy strategy = new NakedPairsStrategy();

    @Test
    void detectsNakedPairAndSuggestsElimination() {
        Board board = new Board();
        board.setFixed(0, 2, 4);
        board.setFixed(0, 3, 5);
        board.setFixed(0, 4, 6);
        board.setFixed(0, 6, 7);
        board.setFixed(0, 7, 8);
        board.setFixed(0, 8, 9);

        board.setFixed(1, 0, 3);
        board.setFixed(2, 1, 3);

        Optional<Step> step = strategy.next(board);

        assertThat(step).isPresent();
        Step s = step.get();
        assertThat(s.getType()).isEqualTo(Step.Type.NAKED_PAIR);
        assertThat(s.getDigit()).isEqualTo(1);
        assertThat(s.getPosition()).isEqualTo(new Position(0, 5));
        assertThat(s.getExplanation()).contains("naked pair").contains("(1, 2)");
    }

    @Test
    void returnsEmptyWhenNoPairsExist() {
        Board board = new Board();

        assertThat(strategy.next(board)).isEmpty();
    }
}
