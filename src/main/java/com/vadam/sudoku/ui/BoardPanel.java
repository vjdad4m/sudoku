package com.vadam.sudoku.ui;

import com.vadam.sudoku.config.Settings;
import com.vadam.sudoku.controller.GameController;
import com.vadam.sudoku.model.Board;
import com.vadam.sudoku.model.Position;
import com.vadam.sudoku.service.GameService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardPanel extends JPanel {
    private final GameService game;
    private final GameController controller;
    private final Settings settings;
    private int selRow = 0;
    private int selCol = 0;

    /**
     * Létrehozza a tábla kirajzolásáért felelős panelt, és regisztrálja a bemeneti kezelőket.
     */
    public BoardPanel(GameService game, GameController controller, Settings settings) {
        this.game = game;
        this.controller = controller;
        this.settings = settings;
        setFocusable(true);
        setPreferredSize(new Dimension(720, 720));
        setBackground(Color.WHITE);

        game.bus().add(e -> {
            if (e.getType() == com.vadam.sudoku.model.event.GameEvent.Type.BOARD_CHANGED
                    || e.getType() == com.vadam.sudoku.model.event.GameEvent.Type.NEW_GAME)
                repaint();
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocusInWindow();
                int size = Math.min(getWidth(), getHeight());
                int cell = size / 9;
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;
                int c = (e.getX() - x) / cell;
                int r = (e.getY() - y) / cell;
                if (r >= 0 && r < 9 && c >= 0 && c < 9) {
                    selRow = r;
                    selCol = c;
                    repaint();
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code >= KeyEvent.VK_1 && code <= KeyEvent.VK_9) {
                    int d = code - KeyEvent.VK_0;
                    applyDigit(d);
                } else if (code == KeyEvent.VK_BACK_SPACE || code == KeyEvent.VK_DELETE || code == KeyEvent.VK_0) {
                    controller.setValue(new Position(selRow, selCol), 0);
                } else if (code == KeyEvent.VK_LEFT) {
                    selCol = Math.max(0, selCol - 1);
                    repaint();
                } else if (code == KeyEvent.VK_RIGHT) {
                    selCol = Math.min(8, selCol + 1);
                    repaint();
                } else if (code == KeyEvent.VK_UP) {
                    selRow = Math.max(0, selRow - 1);
                    repaint();
                } else if (code == KeyEvent.VK_DOWN) {
                    selRow = Math.min(8, selRow + 1);
                    repaint();
                } else if (code == KeyEvent.VK_H) {
                    controller.showHint(false);
                } else if (code == KeyEvent.VK_C) {
                    controller.check();
                } else if (code == KeyEvent.VK_S) {
                    controller.solve();
                } else if (code == KeyEvent.VK_Z) {
                    controller.undo();
                } else if (code == KeyEvent.VK_Y) {
                    controller.redo();
                } else if (code == KeyEvent.VK_P) {
                    settings.setPencilMode(!settings.isPencilMode());
                    repaint();
                }
            }
        });
    }

    /**
     * A kijelölt cellában értéket vagy ceruzajegyzetet alkalmaz a módtól függően.
     */
    private void applyDigit(int d) {
        Position p = new Position(selRow, selCol);
        if (settings.isPencilMode()) {
            controller.toggleNote(p, d);
        } else {
            controller.setValue(p, d);
        }
    }

    /**
     * Megrajzolja a táblát, a kijelöléseket, értékeket és ceruzajegyeket.
     */
    @Override
    protected void paintComponent(Graphics graw) {
        super.paintComponent(graw);
        Graphics2D g = (Graphics2D) graw.create();
        int size = Math.min(getWidth(), getHeight());
        int cell = size / 9;
        int x0 = (getWidth() - size) / 2;
        int y0 = (getHeight() - size) / 2;

        g.setColor(new Color(230, 240, 255));
        g.fillRect(x0, y0 + selRow * cell, size, cell);
        g.fillRect(x0 + selCol * cell, y0, cell, size);
        g.setColor(new Color(235, 245, 255));
        int br = (selRow / 3) * 3, bc = (selCol / 3) * 3;
        g.fillRect(x0 + bc * cell, y0 + br * cell, 3 * cell, 3 * cell);

        g.setColor(Color.WHITE);
        g.fillRect(x0, y0, size, size);

        int selectedValue = game.board().get(selRow, selCol);
        if (settings.isHighlightSameDigits() && selectedValue != 0) {
            g.setColor(new Color(220, 250, 250));
            for (int rr = 0; rr < 9; rr++) {
                for (int cc = 0; cc < 9; cc++) {
                    if (game.board().get(rr, cc) == selectedValue) {
                        g.fillRect(x0 + cc * cell, y0 + rr * cell, cell, cell);
                    }
                }
            }
        }

        Board b = game.board();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int vx = x0 + c * cell;
                int vy = y0 + r * cell;
                int v = b.get(r, c);
                boolean conflict = settings.isErrorHighlight() && v != 0
                        && b.conflicts(new com.vadam.sudoku.model.Position(r, c), v);
                if (conflict) {
                    g.setColor(new Color(255, 230, 230));
                    g.fillRect(vx, vy, cell, cell);
                }
                if (v != 0) {
                    g.setColor(Color.BLACK);
                    Font f = g.getFont().deriveFont(b.isFixed(r, c) ? Font.BOLD : Font.PLAIN, cell * 0.6f);
                    g.setFont(f);
                    String s = String.valueOf(v);
                    FontMetrics fm = g.getFontMetrics();
                    int tx = vx + (cell - fm.stringWidth(s)) / 2;
                    int ty = vy + (cell + fm.getAscent() - fm.getDescent()) / 2;
                    g.drawString(s, tx, ty);
                } else {
                    int bits = b.notes(r, c).raw();
                    if (bits != 0) {
                        g.setColor(Color.GRAY);
                        Font f = g.getFont().deriveFont(Font.PLAIN, cell * 0.20f);
                        g.setFont(f);
                        for (int d = 1; d <= 9; d++) {
                            if ((bits & (1 << d)) != 0) {
                                int subR = (d - 1) / 3, subC = (d - 1) % 3;
                                int tx = vx + (int) ((subC + 0.5) * cell / 3.0);
                                int ty = vy + (int) ((subR + 0.65) * cell / 3.0);
                                String s = String.valueOf(d);
                                FontMetrics fm = g.getFontMetrics();
                                g.drawString(s, tx - fm.stringWidth(s) / 2, ty);
                            }
                        }
                    }
                }
            }
        }

        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= 9; i++) {
            g.setStroke(new BasicStroke(i % 3 == 0 ? 3f : 1f));
            g.drawLine(x0 + i * cell, y0, x0 + i * cell, y0 + size);
            g.drawLine(x0, y0 + i * cell, x0 + size, y0 + i * cell);
        }

        g.setColor(Color.BLUE);
        g.setStroke(new BasicStroke(2f));
        g.drawRect(x0 + selCol * cell, y0 + selRow * cell, cell, cell);
        g.dispose();
    }
}
