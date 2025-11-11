package com.vadam.sudoku.service;

import com.vadam.sudoku.config.Settings;
import com.vadam.sudoku.exception.InvalidMoveException;
import com.vadam.sudoku.generator.SudokuGenerator;
import com.vadam.sudoku.model.*;
import com.vadam.sudoku.model.event.EventBus;
import com.vadam.sudoku.model.event.GameEvent;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

public class GameService {
    private final Board board = new Board();
    private final EventBus bus;
    private final Settings settings;
    private Difficulty difficulty = Difficulty.EASY;
    private final Deque<Move> undo = new ArrayDeque<>();
    private final Deque<Move> redo = new ArrayDeque<>();

    public GameService(EventBus bus, Settings settings) {
        this.bus = bus;
        this.settings = settings;
    }

    public Board board() {
        return board;
    }

    public EventBus bus() {
        return bus;
    }

    public Settings settings() {
        return settings;
    }

    public Difficulty difficulty() {
        return difficulty;
    }

    public void newGame(Board puizzle, Difficulty d) {
        difficulty = d;
        board.setAll(puizzle.values(), puizzle.fixed());
        undo.clear();
        redo.clear();
        bus.publish(new GameEvent(GameEvent.Type.NEW_GAME, "New game: " + d));
        bus.publish(new GameEvent(GameEvent.Type.BOARD_CHANGED, null));
    }

    public void setValue(Position p, int digit) {
        if (board.isFixed(p)) {
            throw new InvalidMoveException("Cell is fixed");
        }
        int before = board.get(p);
        if (before == digit) {
            return;
        }
        board.setValue(p, digit);
        undo.push(new Move.ValueMove(p, before, digit));
        redo.clear();
        if (settings.isAutoPencilUpdate() && digit != 0) {
            for (int i = 0; i < 9; i++) {
                if (i != p.col()) {
                    board.notes(p.row(), i).remove(digit);
                }
            }
            for (int i = 0; i < 9; i++) {
                if (i != p.row()) {
                    board.notes(i, p.col()).remove(digit);
                }
            }
            int br = (p.row() / 3) * 3, bc = (p.col() / 3) * 3;
            for (int r = br; r < br + 3; r++) {
                for (int c = bc; c < bc + 3; c++) {
                    if (r != p.row() || c != p.col()) {
                        board.notes(r, c).remove(digit);
                    }
                }
            }
        }
    }

    public void clearValue(Position p) {
        setValue(p, 0);
    }

    public void toggleNote(Position p, int digit) {
        if (board.isFixed(p)) {
            return;
        }
        board.notes(p).toggle(digit);
        undo.push(new Move.NoteToggleMove(p, digit));
        redo.clear();
        bus.publish(new GameEvent(GameEvent.Type.BOARD_CHANGED, null));
    }

    public void undo() {
        if (undo.isEmpty()) {
            return;
        }
        Move m = undo.pop();
        if (m instanceof Move.ValueMove vm) {
            board.setValue(vm.pos, vm.before);
        } else if (m instanceof Move.NoteToggleMove nm) {
            board.notes(nm.pos).toggle(nm.digit);
        }
        redo.push(m);
        bus.publish(new GameEvent(GameEvent.Type.BOARD_CHANGED, null));
    }

    public void redo() {
        if (redo.isEmpty())
            return;
        Move m = redo.pop();
        if (m instanceof Move.ValueMove vm) {
            board.setValue(vm.pos, vm.after);
        } else if (m instanceof Move.NoteToggleMove nm) {
            board.notes(nm.pos).toggle(nm.digit);
        }
        undo.push(m);
    }

    public Optional<String> check() {
        if (!board.isValidSoFar()) {
            return Optional.of("Conflicts found.");
        }
        if (board.isSolved()) {
            return Optional.of("Solved!");
        }
        return Optional.empty();
    }
}
