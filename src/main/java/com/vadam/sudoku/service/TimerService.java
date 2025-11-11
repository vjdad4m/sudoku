package com.vadam.sudoku.service;

import com.vadam.sudoku.model.event.EventBus;
import com.vadam.sudoku.model.event.GameEvent;

import java.util.concurrent.atomic.AtomicLong;

import javax.swing.Timer;

public class TimerService {
    private final AtomicLong elapsed = new AtomicLong(0);
    private long lastStart = 0;
    private boolean running = false;
    private final Timer timer;
    private final EventBus bus;

    public TimerService(EventBus bus) {
        this.bus = bus;
        timer = new Timer(1000, e -> bus.publish((new GameEvent(GameEvent.Type.TIMER_TICK, null))));
    }

    public void start() {
        if (!running) {
            lastStart = System.currentTimeMillis();
            running = true;
            timer.start();
        }
    }

    public void stop() {
        if (running) {
            elapsed.addAndGet(System.currentTimeMillis() - lastStart);
            running = false;
            timer.stop();
        }
    }

    public void reset() {
        elapsed.set(0);
        lastStart = System.currentTimeMillis();
    }

    public void setElapsedMillis(long millis) {
        elapsed.set(Math.max(0, millis));
        lastStart = System.currentTimeMillis();
    }

    public long elapsedMillis() {
        return running ? elapsed.get() + (System.currentTimeMillis() - lastStart) : elapsed.get();
    }

    public boolean isRunning() {
        return running;
    }
}
