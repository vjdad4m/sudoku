package com.vadam.sudoku.model.event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {
    private final List<GameEventListener> listeners = new CopyOnWriteArrayList<>();

    /**
     * Feliratkoztat egy játékesemény hallgatót.
     */
    public void add(GameEventListener l) {
        listeners.add(l);
    }

    /**
     * Eltávolít egy korábban hozzáadott hallgatót.
     */
    public void remove(GameEventListener l) {
        listeners.remove(l);
    }

    /**
     * Elküldi az eseményt minden regisztrált hallgatónak.
     */
    public void publish(GameEvent e) {
        for (GameEventListener l : listeners) {
            l.onEvent(e);
        }
    }
}
