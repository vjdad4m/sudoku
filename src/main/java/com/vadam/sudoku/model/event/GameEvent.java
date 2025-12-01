package com.vadam.sudoku.model.event;

public class GameEvent {
    public enum Type {
        BOARD_CHANGED, NEW_GAME, TIMER_TICK, SOLVED, MESSAGE
    }

    public static final String AUTO_SOLVE = "AUTO_SOLVE";

    private final Type type;
    private final String message;

    /**
     * Új eseményt hoz létre a típussal és opcionális üzenettel.
     */
    public GameEvent(Type type, String message) {
        this.type = type;
        this.message = message;
    }

    /**
     * Visszaadja az esemény típusát.
     */
    public Type getType() {
        return type;
    }

    /**
     * Visszaadja az eseményhez tartozó üzenetet vagy nullt.
     */
    public String getMessage() {
        return message;
    }
}
