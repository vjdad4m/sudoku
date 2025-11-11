package com.vadam.sudoku.ui;

import com.vadam.sudoku.config.Settings;
import com.vadam.sudoku.controller.GameController;
import com.vadam.sudoku.model.Difficulty;
import com.vadam.sudoku.service.GameService;
import com.vadam.sudoku.service.TimerService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {
    private final GameController controller;
    private final GameService game;
    private final TimerService timer;
    private final Settings settings;
    private final BoardPanel boardPanel;
    private final StatusBar statusBar;

    public MainWindow(GameController controller, GameService game, TimerService timer, Settings settings) {
        super("Sudoku");
        this.controller = controller;
        this.game = game;
        this.timer = timer;
        this.settings = settings;

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
            if (evt.getType() == com.vadam.sudoku.model.event.GameEvent.Type.SOLVED) {
                timer.stop();
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
}
