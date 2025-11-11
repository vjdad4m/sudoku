package com.vadam.sudoku.config;

public class Settings {
    private boolean errorHighlight = true;
    private boolean autoPencilUpdate = true;
    private boolean applyHint = true;
    private boolean highlightSameDigits = true;
    private boolean pencilMode = false;

    public boolean isErrorHighlight() {
        return errorHighlight;
    }

    public void setErrorHighlight(boolean v) {
        this.errorHighlight = v;
    }

    public boolean isAutoPencilUpdate() {
        return autoPencilUpdate;
    }

    public void setAutoPencilUpdate(boolean v) {
        this.autoPencilUpdate = v;
    }

    public boolean isApplyHint() {
        return applyHint;
    }

    public void setApplyHint(boolean v) {
        this.applyHint = v;
    }

    public boolean isHighlightSameDigits() {
        return highlightSameDigits;
    }

    public void setHighlightSameDigits(boolean v) {
        this.highlightSameDigits = v;
    }

    public boolean isPencilMode() {
        return pencilMode;
    }

    public void setPencilMode(boolean pencilMode) {
        this.pencilMode = pencilMode;
    }
}
