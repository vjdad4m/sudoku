package com.vadam.sudoku.model.event;

public interface GameEventListener {
    void onEvent(GameEvent evt);
}
