package com.vadam.sudoku.model.event;

public class GameEvent {
    public enum Type {
        BOARD_CHANGED, NEW_GAME, TIMER_TICK, SOLVED, MESSAGE
    }

    private final Type type;
    private final String message;

    public GameEvent(Type type, String message) {
        this.type = type;
        this.message = message;
    }

    public Type getType() {
        return type;
    }
    
    public String getMessage() {
        return message;
    }
}
