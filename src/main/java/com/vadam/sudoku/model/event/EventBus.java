package com.vadam.sudoku.model.event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {
    private final List<GameEventListener> listeners = new CopyOnWriteArrayList<>();

    public void add(GameEventListener l) {
        listeners.add(l);
    }

    public void remove(GameEventListener l) {
        listeners.remove(l);
    }

    public void publish(GameEvent e) {
        for (GameEventListener l : listeners) {
            l.onEvent(e);
        }
    }
}
