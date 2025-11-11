package com.vadam.sudoku.solver;

import java.util.Optional;

import com.vadam.sudoku.model.Board;

public interface Strategy {
    Optional<Step> next(Board board);
}
