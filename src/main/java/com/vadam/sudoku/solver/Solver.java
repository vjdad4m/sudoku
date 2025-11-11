package com.vadam.sudoku.solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vadam.sudoku.model.Board;
import com.vadam.sudoku.model.CandidateSet;
import com.vadam.sudoku.model.Position;
import com.vadam.sudoku.util.ValidationUtils;

public class Solver {
    private final List<Strategy> strategies = new ArrayList<>();

    public Solver(List<Strategy> strategies) {
        this.strategies.addAll(strategies);
    }

    public Optional<Step> nextStep(Board board) {
        for (Strategy s : strategies) {
            Optional<Step> step = s.next(board);
            if (step.isPresent()) {
                return step;
            }
        }
        return Optional.empty();
    }

    public List<Step> solveLogically(Board board) {
        List<Step> steps = new ArrayList<>();
        while (true) {
            Optional<Step> s = nextStep(board);
            if (s.isEmpty()) {
                break;
            }
            Step st = s.get();
            board.setValue(st.getPosition(), st.getDigit());
            steps.add(st);
        }
        return steps;
    }

    public boolean solveBacktracking(Board board) {
        int r = -1;
        int c = -1;
        int minCandidates = 10;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.get(i, j) == 0) {
                    int count = board.computeAllowed(new Position(i, j)).size();
                    if (count == 0) {
                        return false;
                    }
                    if (count < minCandidates) {
                        minCandidates = count;
                        r = i;
                        c = j;
                        if (count == 1) {
                            break;
                        }
                    }
                }
            }
        }
        if (r == -1) {
            return ValidationUtils.isValid(board.values());
        }
        CandidateSet allowed = board.computeAllowed(new Position(r, c));
        for (int d = 1; d <= 9; d++) {
            if (allowed.has(d)) {
                board.setValue(r, c, d);
                if (solveBacktracking(board))
                    return true;
                board.setValue(r, c, 0);
            }
        }
        return false;
    }

    public int countSolutions(Board board, int maxCount) {
        return count(board, maxCount, 0);
    }

    private int count(Board b, int max, int found) {
        if (found >= max) {
            return found;
        }
        int r = -1;
        int c = -1;
        int min = 10;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (b.get(i, j) == 0) {
                    int cnt = b.computeAllowed(new Position(i, j)).size();
                    if (cnt == 0) {
                        return found;
                    }
                    if (cnt < min) {
                        min = cnt;
                        r = i;
                        c = j;
                    }
                }
            }
        }
        if (r == -1)
            return found + 1;
        CandidateSet allowed = b.computeAllowed(new Position(r, c));
        for (int d = 1; d <= 9; d++) {
            if (allowed.has(d)) {
                b.setValue(r, c, d);
                found = count(b, max, found);
                if (found >= max) {
                    return found;
                }
                b.setValue(r, c, 0);
            }
        }
        return found;
    }
}
