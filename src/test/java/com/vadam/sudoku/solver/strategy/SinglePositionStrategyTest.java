package com.vadam.sudoku.solver.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import com.vadam.sudoku.model.Board;
import com.vadam.sudoku.model.Position;
import com.vadam.sudoku.solver.Step;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class SinglePositionStrategyTest {
    private final SinglePositionStrategy strategy = new SinglePositionStrategy();

    @Test
    void detectsRowWithSingleValidPosition() {
        Board board = new Board();
        for (int c = 1; c < 9; c++) {
            board.setFixed(3, c, 5);
        }

        Optional<Step> step = strategy.next(board);

        assertThat(step).isPresent();
        Step s = step.get();
        assertThat(s.getType()).isEqualTo(Step.Type.SINGLE_POSITION);
        assertThat(s.getDigit()).isEqualTo(5);
        assertThat(s.getPosition()).isEqualTo(new Position(0, 0));
        assertThat(s.getExplanation()).contains("Row 1");
    }

    @Test
    void detectsColumnWithSingleValidPosition() {
        Board board = new Board();
        for (int r = 0; r < 8; r++) {
            board.setFixed(r, 0, r + 1);
        }

        Optional<Step> step = strategy.next(board);

        assertThat(step).isPresent();
        Step s = step.get();
        assertThat(s.getType()).isEqualTo(Step.Type.SINGLE_POSITION);
        assertThat(s.getDigit()).isEqualTo(9);
        assertThat(s.getPosition()).isEqualTo(new Position(8, 0));
        assertThat(s.getExplanation()).contains("Column 1");
    }

    @Test
    void detectsBlockWithSingleValidPosition() {
        Board board = StrategyTestUtils.board(
                "876......",
                "5.3......",
                "291......",
                ".........",
                ".........",
                ".........",
                ".........",
                ".........",
                ".........");

        Optional<Step> step = strategy.next(board);

        assertThat(step).isPresent();
        Step s = step.get();
        assertThat(s.getType()).isEqualTo(Step.Type.SINGLE_POSITION);
        assertThat(s.getDigit()).isEqualTo(4);
        assertThat(s.getPosition()).isEqualTo(new Position(1, 1));
        assertThat(s.getExplanation()).contains("Block (1,1)");
    }
}
