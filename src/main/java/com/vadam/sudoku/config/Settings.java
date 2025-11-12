package com.vadam.sudoku.config;

import java.util.function.Consumer;

public class Settings {
    private boolean errorHighlight = true;
    private boolean autoPencilUpdate = true;
    private boolean applyHint = true;
    private boolean highlightSameDigits = true;
    private boolean pencilMode = false;
    private Consumer<Settings> onChange;

    public boolean isErrorHighlight() {
        return errorHighlight;
    }

    public void setErrorHighlight(boolean v) {
        if (this.errorHighlight != v) {
            this.errorHighlight = v;
            notifyChanged();
        }
    }

    public boolean isAutoPencilUpdate() {
        return autoPencilUpdate;
    }

    public void setAutoPencilUpdate(boolean v) {
        if (this.autoPencilUpdate != v) {
            this.autoPencilUpdate = v;
            notifyChanged();
        }
    }

    public boolean isApplyHint() {
        return applyHint;
    }

    public void setApplyHint(boolean v) {
        if (this.applyHint != v) {
            this.applyHint = v;
            notifyChanged();
        }
    }

    public boolean isHighlightSameDigits() {
        return highlightSameDigits;
    }

    public void setHighlightSameDigits(boolean v) {
        if (this.highlightSameDigits != v) {
            this.highlightSameDigits = v;
            notifyChanged();
        }
    }

    public boolean isPencilMode() {
        return pencilMode;
    }

    public void setPencilMode(boolean pencilMode) {
        if (this.pencilMode != pencilMode) {
            this.pencilMode = pencilMode;
            notifyChanged();
        }
    }

    public void setChangeListener(Consumer<Settings> listener) {
        this.onChange = listener;
    }

    private void notifyChanged() {
        if (onChange != null) {
            onChange.accept(this);
        }
    }
}
