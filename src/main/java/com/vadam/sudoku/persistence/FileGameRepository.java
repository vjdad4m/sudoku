package com.vadam.sudoku.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.vadam.sudoku.exception.PersistenceException;
import com.vadam.sudoku.model.GameState;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FileGameRepository implements GameRepository {
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public void save(Path file, GameState state) {
        try {
            Files.createDirectories(file.getParent());
            mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), state);
        } catch (IOException e) {
            throw new PersistenceException("Failed to save game", e);
        }
    }

    @Override
    public Optional<GameState> load(Path file) {
        try {
            if (!Files.exists(file)) {
                return Optional.empty();
            }
            GameState state = mapper.readValue(file.toFile(), GameState.class);
            return Optional.of(state);
        } catch (IOException e) {
            throw new PersistenceException("Failed to load game", e);
        }
    }
}
