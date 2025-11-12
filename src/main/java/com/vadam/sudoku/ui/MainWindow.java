package com.vadam.sudoku.ui;

import com.vadam.sudoku.config.Settings;
import com.vadam.sudoku.controller.GameController;
import com.vadam.sudoku.model.Difficulty;
import com.vadam.sudoku.model.event.GameEvent;
import com.vadam.sudoku.service.GameService;
import com.vadam.sudoku.service.StatisticsService;
import com.vadam.sudoku.service.TimerService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {
    private final GameController controller;
    private final GameService game;
    private final TimerService timer;
    private final Settings settings;
    private final StatisticsService stats;
    private final BoardPanel boardPanel;
    private final StatusBar statusBar;

    public MainWindow(GameController controller, GameService game, TimerService timer, Settings settings,
            StatisticsService stats) {
        super("Sudoku");
        this.controller = controller;
        this.game = game;
        this.timer = timer;
        this.settings = settings;
        this.stats = stats;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 800);
        setLocationRelativeTo(null);

        boardPanel = new BoardPanel(game, controller, settings);
        statusBar = new StatusBar(game, timer);

        setLayout(new BorderLayout());
        add(buildToolBar(), BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
        setJMenuBar(buildMenu());
        game.bus().add(evt -> {
            if (evt.getType() == GameEvent.Type.SOLVED) {
                timer.stop();
                if (!GameEvent.AUTO_SOLVE.equals(evt.getMessage())) {
                    stats.recordGame(game.difficulty(), timer.elapsedMillis());
                }
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "Solved!"));
            }
        });

    }

    private JToolBar buildToolBar() {
        JToolBar tb = new JToolBar();
        tb.setFloatable(false);
        tb.add(new JButton(new AbstractAction("New") {
            public void actionPerformed(ActionEvent e) {
                showNewGameDialog();
            }
        }));
        tb.add(new JButton(new AbstractAction("Hint") {
            public void actionPerformed(ActionEvent e) {
                controller.showHint(false);
            }
        }));
        tb.add(new JButton(new AbstractAction("Check") {
            public void actionPerformed(ActionEvent e) {
                controller.check();
            }
        }));
        tb.add(new JButton(new AbstractAction("Solve") {
            public void actionPerformed(ActionEvent e) {
                controller.solve();
            }
        }));
        tb.addSeparator();
        tb.add(new JButton(new AbstractAction("Undo") {
            public void actionPerformed(ActionEvent e) {
                controller.undo();
            }
        }));
        tb.add(new JButton(new AbstractAction("Redo") {
            public void actionPerformed(ActionEvent e) {
                controller.redo();
            }
        }));
        tb.addSeparator();
        tb.add(new JButton(new AbstractAction("Save") {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                if (fc.showSaveDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
                    controller.save(fc.getSelectedFile().toPath());
                }
            }
        }));
        tb.add(new JButton(new AbstractAction("Load") {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                if (fc.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
                    controller.load(fc.getSelectedFile().toPath());
                }
            }
        }));
        return tb;
    }

    private JMenuBar buildMenu() {
        JMenuBar mb = new JMenuBar();
        JMenu gameM = new JMenu("Game");
        gameM.add(new JMenuItem(new AbstractAction("New...") {
            public void actionPerformed(ActionEvent e) {
                showNewGameDialog();
            }
        }));
        gameM.add(new JMenuItem(new AbstractAction("Import...") {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                if (fc.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
                    controller.importPuzzle(fc.getSelectedFile().toPath());
                }
            }
        }));
        gameM.add(new JMenuItem(new AbstractAction("Export...") {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                if (fc.showSaveDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
                    controller.exportPuzzle(fc.getSelectedFile().toPath());
                }
            }
        }));
        gameM.addSeparator();
        gameM.add(new JMenuItem(new AbstractAction("Exit") {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }));
        mb.add(gameM);

        JMenu options = new JMenu("Options");
        JCheckBoxMenuItem err = new JCheckBoxMenuItem("Error highlighting", settings.isErrorHighlight());
        err.addActionListener(e -> settings.setErrorHighlight(err.isSelected()));
        options.add(err);
        JCheckBoxMenuItem apu = new JCheckBoxMenuItem("Auto pencil update", settings.isAutoPencilUpdate());
        apu.addActionListener(e -> settings.setAutoPencilUpdate(apu.isSelected()));
        options.add(apu);
        JCheckBoxMenuItem app = new JCheckBoxMenuItem("Apply hint automatically", settings.isApplyHint());
        app.addActionListener(e -> settings.setApplyHint(app.isSelected()));
        options.add(app);
        JCheckBoxMenuItem same = new JCheckBoxMenuItem("Highlight same digits", settings.isHighlightSameDigits());
        same.addActionListener(e -> settings.setHighlightSameDigits(same.isSelected()));
        options.add(same);
        JCheckBoxMenuItem pencil = new JCheckBoxMenuItem("Pencil mode", settings.isPencilMode());
        pencil.addActionListener(e -> settings.setPencilMode(pencil.isSelected()));
        options.add(pencil);
        mb.add(options);

        JMenu statsMenu = new JMenu("Statistics");
        statsMenu.add(new JMenuItem(new AbstractAction("View...") {
            public void actionPerformed(ActionEvent e) {
                showStatisticsDialog();
            }
        }));
        mb.add(statsMenu);
        return mb;
    }

    private void showNewGameDialog() {
        Difficulty[] diffs = Difficulty.values();
        Difficulty sel = (Difficulty) JOptionPane.showInputDialog(this,
                "Choose difficulty:", "New game",
                JOptionPane.PLAIN_MESSAGE, null, diffs, diffs[1]);
        if (sel != null)
            controller.newGame(sel);
    }

    private void showStatisticsDialog() {
        String[] cols = { "Difficulty", "Games Played", "Best Time" };
        Object[][] rows = new Object[Difficulty.values().length][cols.length];
        Difficulty[] diffs = Difficulty.values();
        for (int i = 0; i < diffs.length; i++) {
            Difficulty d = diffs[i];
            rows[i][0] = d.name();
            rows[i][1] = stats.gameCount(d);
            rows[i][2] = stats.bestTime(d).map(this::formatDuration).orElse("—");
        }
        JTable table = new JTable(rows, cols);
        table.setEnabled(false);
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(400, 200));
        JOptionPane.showMessageDialog(this, scroll, "Statistics", JOptionPane.INFORMATION_MESSAGE);
    }

    private String formatDuration(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long remaining = seconds % 60;
        return String.format("%02d:%02d", minutes, remaining);
    }
}
