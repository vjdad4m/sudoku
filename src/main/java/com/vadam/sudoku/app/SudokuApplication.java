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
import com.vadam.sudoku.service.StatisticsService;
import com.vadam.sudoku.service.TimerService;
import com.vadam.sudoku.ui.MainWindow;

public class SudokuApplication {
    /**
     * Belépési pont a Sudoku Swing alkalmazásba.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
            }
            EventBus bus = new EventBus();
            Settings settings = PreferencesManager.loadOrDefault();
            settings.setChangeListener(PreferencesManager::save);
            TimerService timer = new TimerService(bus);
            GameService game = new GameService(bus, settings);
            StatisticsService stats = new StatisticsService();
            GameController controller = new GameController(game, new SudokuGenerator(), new HintService(), timer);
            MainWindow win = new MainWindow(controller, game, timer, settings, stats);
            win.setVisible(true);
            controller.newGame(Difficulty.EASY);
        });
    }
}
