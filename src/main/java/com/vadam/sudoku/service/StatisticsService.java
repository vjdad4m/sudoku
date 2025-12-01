package com.vadam.sudoku.service;

import com.vadam.sudoku.exception.PersistenceException;
import com.vadam.sudoku.model.Difficulty;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StatisticsService {
    private final Path file;
    private final ObjectMapper mapper = new ObjectMapper();
    private Map<String, Object> data = new HashMap<>();

    /**
     * Megadott elérési úttal inicializálja a szolgáltatást.
     */
    public StatisticsService(Path file) {
        this.file = file;
        load();
    }

    /**
     * Alapértelmezett útvonalat használ a felhasználó könyvtárán belül a statisztikákhoz.
     */
    public StatisticsService() {
        String home = System.getProperty("user.home");
        if (home == null || home.isBlank()) {
            home = ".";
        }
        this.file = Paths.get(home, ".sudoku", "statistics.json");
        load();
    }

    /**
     * Betölti a statisztikákat JSON formátumból.
     */
    private void load() {
        try {
            if (Files.exists(file)) {
                data = mapper.readValue(file.toFile(), Map.class);
            }
        } catch (IOException e) {
            data = new HashMap<>();
        }
    }

    /**
     * Menti a statisztikákat JSON formátumban.
     */
    private void save() {
        try {
            Files.createDirectories(file.getParent());
            mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), data);
        } catch (IOException e) {
            throw new PersistenceException("Could not save statistics", e);
        }
    }

    /**
     * Rögzíti egy játék befejezését a megadott nehézségi szinten és idővel.
    */
    public void recordGame(Difficulty d, long millis) {
        String keyCount = d.name() + ".count";
        String keyBest = d.name() + ".best";
        int count = ((Number) data.getOrDefault(keyCount, 0)).intValue();
        data.put(keyCount, count + 1);
        Number best = (Number) data.get(keyBest);
        if (best == null || millis < best.longValue())
            data.put(keyBest, millis);
        save();
    }

    /**
     * Visszaadja a legjobb időt az adott nehézségi szinten, ha van ilyen.
     */
    public Optional<Long> bestTime(Difficulty d) {
        Number n = (Number) data.get(d.name() + ".best");
        return n == null ? Optional.empty() : Optional.of(n.longValue());
    }

    /**
     * Visszaadja a befejezett játékok számát az adott nehézségi szinten.
     */
    public int gameCount(Difficulty d) {
        return ((Number) data.getOrDefault(d.name() + ".count", 0)).intValue();
    }
}
