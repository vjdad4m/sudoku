package com.vadam.sudoku.app;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.vadam.sudoku.config.PreferencesManager;
import com.vadam.sudoku.config.Settings;
import com.vadam.sudoku.controller.GameController;
import com.vadam.sudoku.generator.SudokuGenerator;
import com.vadam.sudoku.model.Difficulty;
import com.vadam.sudoku.model.event.EventBus;
import com.vadam.sudoku.service.GameService;
import com.vadam.sudoku.service.HintService;
import com.vadam.sudoku.service.TimerService;
import com.vadam.sudoku.ui.MainWindow;

public class SudokuApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
            }
            EventBus bus = new EventBus();
            Settings settings = PreferencesManager.loadOrDefault();
            TimerService timer = new TimerService(bus);
            GameService game = new GameService(bus, settings);
            GameController controller = new GameController(game, new SudokuGenerator(), new HintService(), timer);
            MainWindow win = new MainWindow(controller, game, timer, settings);
            win.setVisible(true);
            controller.newGame(Difficulty.EASY);
        });
    }
}
