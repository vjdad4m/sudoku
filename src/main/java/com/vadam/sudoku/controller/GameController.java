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

    /**
     * Összekapcsolja a játéklogikát a vezérlővel.
     */
    public GameController(GameService game, SudokuGenerator generator, HintService hints, TimerService timer) {
        this.game = game;
        this.generator = generator;
        this.hints = hints;
        this.timer = timer;
    }

    /**
     * Létrehoz és elindít egy új játékot a kiválasztott nehézséggel.
     */
    public void newGame(Difficulty d) {
        Board puzzle = generator.generate(d);
        game.newGame(puzzle, d);
        timer.reset();
        timer.start();
    }

    /**
     * Értéket ír a megadott cellába a játék szolgáltatáson keresztül.
     */
    public void setValue(Position p, int d) {
        game.setValue(p, d);
    }

    /**
     * Átkapcsolja a megadott cella ceruzajegyét.
     */
    public void toggleNote(Position p, int d) {
        game.toggleNote(p, d);
    }

    /**
     * Visszavonja az utolsó lépést, ha létezik.
     */
    public void undo() {
        game.undo();
    }

    /**
     * Újraalkalmazza a legutóbb visszavont lépést, ha létezik.
     */
    public void redo() {
        game.redo();
    }

    /**
     * Megkeresi a következő logikai lépést, opcionálisan alkalmazza, és megjeleníti az indoklást.
     */
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

    /**
     * Ellenőrzi a tábla állapotát és üzenetben jelzi az eredményt.
     */
    public void check() {
        Optional<String> msg = game.check();
        JOptionPane.showMessageDialog(null, msg.orElse("No conflicts so far."));
    }

    /**
     * Megpróbálja logikával, majd visszalépéses kereséssel megoldani a feladványt, és frissíti az órát.
     */
    public void solve() {
        game.markAutoSolvePending();
        boolean wasSolved = game.board().isSolved();
        boolean ok = hints.solveFully(game.board());
        if (!ok) {
            JOptionPane.showMessageDialog(null, "No solution found.");
        }
        timer.stop();
        if (!wasSolved || !game.board().isSolved()) {
            game.refreshBoard();
        }
        game.clearAutoSolvePending();
    }

    /**
     * Elmenti a jelenlegi játékállapotot a megadott elérési útra.
     */
    public void save(Path path) {
        GameState state = new GameState(
                game.board().values(), game.board().fixed(), timer.elapsedMillis(), game.difficulty());
        repo.save(path, state);
    }

    /**
     * Betölt egy korábban elmentett állapotot és visszaállítja az órát is.
     */
    public void load(Path path) {
        Optional<GameState> st = repo.load(path);
        if (st.isPresent()) {
            GameState s = st.get();
            Board b = new Board();
            b.setAll(s.getValues(), s.getFixed());
            game.newGame(b, s.getDifficulty());
            timer.stop();
            timer.setElapsedMillis(s.getElapsedMillis());
            timer.start();
        }
    }

    /**
     * Fájlból importálja a rejtvényt, majd új játékot indít vele.
     */
    public void importPuzzle(Path path) {
        try {
            Board b = io.importFromFile(path);
            game.newGame(b, game.difficulty());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Import failed: " + e.getMessage());
        }
    }

    /**
     * Fájlba exportálja az aktuális rejtvényt.
     */
    public void exportPuzzle(Path path) {
        try {
            io.exportToFile(game.board(), path);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Export failed: " + e.getMessage());
        }
    }
}
