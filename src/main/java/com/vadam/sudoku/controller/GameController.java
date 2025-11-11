package com.vadam.sudoku.controller;

import com.vadam.sudoku.generator.SudokuGenerator;
import com.vadam.sudoku.solver.*;
import com.vadam.sudoku.model.*;
import com.vadam.sudoku.persistence.FileGameRepository;
import com.vadam.sudoku.persistence.ImportExportService;
import com.vadam.sudoku.service.GameService;
import com.vadam.sudoku.service.HintService;
import com.vadam.sudoku.service.TimerService;

import javax.swing.*;
import java.nio.file.Path;
import java.util.Optional;

public class GameController {
    private final GameService game;
    private final SudokuGenerator generator;
    private final HintService hints;
    private final TimerService timer;
    private final FileGameRepository repo = new FileGameRepository();
    private final ImportExportService io = new ImportExportService();

    public GameController(GameService game, SudokuGenerator generator, HintService hints, TimerService timer) {
        this.game = game;
        this.generator = generator;
        this.hints = hints;
        this.timer = timer;
    }

    public void newGame(Difficulty d) {
        Board puzzle = generator.generate(d);
        game.newGame(puzzle, d);
        timer.reset();
        timer.start();
    }

    public void setValue(Position p, int d) {
        game.setValue(p, d);
    }

    public void toggleNote(Position p, int d) {
        game.toggleNote(p, d);
    }

    public void undo() {
        game.undo();
    }

    public void redo() {
        game.redo();
    }

    public void showHint(boolean apply) {
        Optional<Step> s = hints.nextStep(game.board());
        if (s.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No logical step found.");
        } else {
            Step st = s.get();
            if (apply || game.settings().isApplyHint()) {
                game.setValue(st.getPosition(), st.getDigit());
            }
            JOptionPane.showMessageDialog(null, st.getExplanation());
        }
    }

    public void check() {
        Optional<String> msg = game.check();
        JOptionPane.showMessageDialog(null, msg.orElse("No conflicts so far."));
    }

    public void solve() {
        boolean ok = hints.solveFully(game.board());
        if (!ok) {
            JOptionPane.showMessageDialog(null, "No solution found.");
        }
        timer.stop();
    }

    public void save(Path path) {
        GameState state = new GameState(
                game.board().values(), game.board().fixed(), timer.elapsedMillis(), game.difficulty());
        repo.save(path, state);
    }

    public void load(Path path) {
        Optional<GameState> st = repo.load(path);
        if (st.isPresent()) {
            GameState s = st.get();
            Board b = new Board();
            b.setAll(s.getValues(), s.getFixed());
            game.newGame(b, s.getDifficulty());
            timer.stop();
            timer.reset();
        }
    }

    public void importPuzzle(Path path) {
        try {
            Board b = io.importFromFile(path);
            game.newGame(b, game.difficulty());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Import failed: " + e.getMessage());
        }
    }

    public void exportPuzzle(Path path) {
        try {
            io.exportToFile(game.board(), path);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Export failed: " + e.getMessage());
        }
    }
}
