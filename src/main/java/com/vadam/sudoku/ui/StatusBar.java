package com.vadam.sudoku.ui;

import com.vadam.sudoku.model.event.GameEvent;
import com.vadam.sudoku.service.GameService;
import com.vadam.sudoku.service.TimerService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatusBar extends JPanel {
    private final JLabel timeLbl = new JLabel("00:00");
    private final JLabel msgLbl = new JLabel("Ready");

    /**
     * Státuszsávot hoz létre, amely mutatja az időt, és figyel az eseményüzenetekre.
     */
    public StatusBar(GameService game, TimerService timer) {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 12, 0, 12));
        timeLbl.setBorder(new EmptyBorder(0, 0, 0, 8));
        msgLbl.setBorder(new EmptyBorder(0, 8, 0, 0));

        add(timeLbl, BorderLayout.WEST);
        add(msgLbl, BorderLayout.EAST);

        game.bus().add(evt -> {
            if (evt.getType() == GameEvent.Type.NEW_GAME) {
                msgLbl.setText(evt.getMessage());
            } else if (evt.getType() == GameEvent.Type.SOLVED) {
                msgLbl.setText("Solved!");
            }
        });
        new Timer(500, e -> timeLbl.setText(format(timer.elapsedMillis()))).start();
    }

    /**
     * Perc:másodperc formátumban adja vissza az időt.
     */
    private String format(long ms) {
        long s = ms / 1000;
        long m = s / 60;
        long ss = s % 60;
        return String.format("%02d:%02d", m, ss);
    }
}
