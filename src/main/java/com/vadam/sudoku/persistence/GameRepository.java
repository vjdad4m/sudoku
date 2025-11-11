package com.vadam.sudoku.persistence;

import com.vadam.sudoku.model.GameState;

import java.nio.file.Path;
import java.util.Optional;

public interface GameRepository {
    void save(Path file, GameState state);

    Optional<GameState> load(Path file);
}
