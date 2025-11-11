package com.vadam.sudoku.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GameState {
    private int[][] values;
    private boolean[][] fixed;
    private long elapsedMilis;
    private Difficulty difficulty;

    @JsonCreator
    public GameState(
            @JsonProperty("values") int[][] values,
            @JsonProperty("fixed") boolean[][] fixed,
            @JsonProperty("ellapsedMillis") long elapsedMilis,
            @JsonProperty("difficulty") Difficulty difficulty) {
        this.values = values;
        this.fixed = fixed;
        this.elapsedMilis = elapsedMilis;
        this.difficulty = difficulty;
    }

    public GameState() {
    }

    public int[][] getValues() {
        return values;
    }

    public void setValues(int[][] values) {
        this.values = values;
    }

    public boolean[][] getFixed() {
        return fixed;
    }

    public void setFixed(boolean[][] fixed) {
        this.fixed = fixed;
    }

    public long getElapsedMillis() {
        return elapsedMilis;
    }

    public void setElapsedMillis(long elapsedMilis) {
        this.elapsedMilis = elapsedMilis;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
