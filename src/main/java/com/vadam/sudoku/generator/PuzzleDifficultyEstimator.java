package com.vadam.sudoku.generator;

import com.vadam.sudoku.model.Board;

public class PuzzleDifficultyEstimator {
    /**
     * Becslést ad a rejtvény nehézségére a megadott kiinduló számok alapján.
     */
    public int estimate(Board board) {
        int clues = 81 - board.countEmpty();
        return clues;
    }
}
