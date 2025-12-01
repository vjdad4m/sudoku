package com.vadam.sudoku.config;

import java.util.prefs.Preferences;

public class PreferencesManager {
    private static final String NODE = "com.vadam.sudoku.settings";

    /**
     * Segédosztály, nem példányosítható.
     */
    private PreferencesManager() {
    }

    /**
     * Betölti a beállításokat a felhasználó preferenciáiból, hiány esetén alapértelmezést ad.
     */
    public static Settings loadOrDefault() {
        Preferences p = Preferences.userRoot().node(NODE);
        Settings s = new Settings();
        s.setErrorHighlight(p.getBoolean("errorHighlight", true));
        s.setAutoPencilUpdate(p.getBoolean("autoPencilUpdate", true));
        s.setApplyHint(p.getBoolean("applyHint", true));
        s.setHighlightSameDigits(p.getBoolean("highlightSameDigits", true));
        s.setPencilMode(p.getBoolean("pencilMode", false));
        return s;
    }

    /**
     * Elmenti a beállításokat a felhasználó preferenciáiba.
     */
    public static void save(Settings s) {
        Preferences p = Preferences.userRoot().node(NODE);
        p.putBoolean("errorHighlight", s.isErrorHighlight());
        p.putBoolean("autoPencilUpdate", s.isAutoPencilUpdate());
        p.putBoolean("applyHint", s.isApplyHint());
        p.putBoolean("highlightSameDigits", s.isHighlightSameDigits());
        p.putBoolean("pencilMode", s.isPencilMode());
    }
}
