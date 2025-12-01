package com.vadam.sudoku.config;

import java.util.function.Consumer;

public class Settings {
    private boolean errorHighlight = true;
    private boolean autoPencilUpdate = true;
    private boolean applyHint = true;
    private boolean highlightSameDigits = true;
    private boolean pencilMode = false;
    private Consumer<Settings> onChange;

    /**
     * Jelzi, hogy kiemeljük-e az ütközésben lévő cellákat.
     */
    public boolean isErrorHighlight() {
        return errorHighlight;
    }

    /**
     * Beállítja az ütközéskiemelés kapcsolót és értesíti a hallgatót, ha változott.
     */
    public void setErrorHighlight(boolean v) {
        if (this.errorHighlight != v) {
            this.errorHighlight = v;
            notifyChanged();
        }
    }

    /**
     * Visszaadja, hogy logikai lépés után automatikusan frissüljenek-e a ceruzajegyek.
     */
    public boolean isAutoPencilUpdate() {
        return autoPencilUpdate;
    }

    /**
     * Beállítja az automatikus ceruzajegy-frissítést és jelzi a változást.
     */
    public void setAutoPencilUpdate(boolean v) {
        if (this.autoPencilUpdate != v) {
            this.autoPencilUpdate = v;
            notifyChanged();
        }
    }

    /**
     * Megmondja, hogy a tipp automatikusan beírja-e a talált számot.
     */
    public boolean isApplyHint() {
        return applyHint;
    }

    /**
     * Kapcsolja a hint automatikus alkalmazását és jelzi a változást.
     */
    public void setApplyHint(boolean v) {
        if (this.applyHint != v) {
            this.applyHint = v;
            notifyChanged();
        }
    }

    /**
     * Visszaadja, hogy kiemeljük-e a megegyező számokat.
     */
    public boolean isHighlightSameDigits() {
        return highlightSameDigits;
    }

    /**
     * Beállítja az azonos számok kiemelését és jelzi a változást.
     */
    public void setHighlightSameDigits(boolean v) {
        if (this.highlightSameDigits != v) {
            this.highlightSameDigits = v;
            notifyChanged();
        }
    }

    /**
     * Megadja, hogy alapból ceruzamódban vagyunk-e.
     */
    public boolean isPencilMode() {
        return pencilMode;
    }

    /**
     * Beállítja a ceruzamód kapcsolót és jelzi a változást.
     */
    public void setPencilMode(boolean pencilMode) {
        if (this.pencilMode != pencilMode) {
            this.pencilMode = pencilMode;
            notifyChanged();
        }
    }

    /**
     * Eltárolja a változásfigyelő hallgatót.
     */
    public void setChangeListener(Consumer<Settings> listener) {
        this.onChange = listener;
    }

    /**
     * Értesíti a hallgatót, amennyiben van ilyen beállítva.
     */
    private void notifyChanged() {
        if (onChange != null) {
            onChange.accept(this);
        }
    }
}
