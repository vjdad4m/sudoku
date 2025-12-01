package com.vadam.sudoku.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GameState {
    private int[][] values;
    private boolean[][] fixed;
    private long elapsedMilis;
    private Difficulty difficulty;

    /**
     * JSON deszerializáláshoz szükséges állapot konstruktor.
     */
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

    /**
     * Üres konstruktor keretrendszer számára.
     */
    public GameState() {
    }

    /**
     * Visszaadja a tábla értékeit.
     */
    public int[][] getValues() {
        return values;
    }

    /**
     * Beállítja a tábla értékeit.
     */
    public void setValues(int[][] values) {
        this.values = values;
    }

    /**
     * Visszaadja a fix cellákat jelölő tömböt.
     */
    public boolean[][] getFixed() {
        return fixed;
    }

    /**
     * Beállítja a fix cellákat jelölő tömböt.
     */
    public void setFixed(boolean[][] fixed) {
        this.fixed = fixed;
    }

    /**
     * Visszaadja az eltelt időt ezredmásodpercben.
     */
    public long getElapsedMillis() {
        return elapsedMilis;
    }

    /**
     * Beállítja az eltelt időt ezredmásodpercben.
     */
    public void setElapsedMillis(long elapsedMilis) {
        this.elapsedMilis = elapsedMilis;
    }

    /**
     * Visszaadja a játék nehézségét.
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Beállítja a játék nehézségét.
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
