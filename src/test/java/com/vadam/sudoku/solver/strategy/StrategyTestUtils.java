package com.vadam.sudoku.solver.strategy;

import com.vadam.sudoku.model.Board;

final class StrategyTestUtils {
    private StrategyTestUtils() {
    }

    static Board board(String... rows) {
        if (rows.length != 9) {
            throw new IllegalArgumentException("Expected 9 rows");
        }
        Board board = new Board();
        for (int r = 0; r < 9; r++) {
            String row = rows[r];
            if (row.length() != 9) {
                throw new IllegalArgumentException("Expected 9 columns, got " + row.length());
            }
            for (int c = 0; c < 9; c++) {
                char ch = row.charAt(c);
                if (ch >= '1' && ch <= '9') {
                    board.setFixed(r, c, ch - '0');
                }
            }
        }
        return board;
    }
}
