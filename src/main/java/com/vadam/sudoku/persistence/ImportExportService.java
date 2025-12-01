package com.vadam.sudoku.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.vadam.sudoku.model.Board;

public class ImportExportService {
    /**
     * 81 karakteres szövegből feltölti a táblát fix értékekkel.
     */
    public Board importFromString(String s) {
        s = s.replaceAll("[\r\n]", "");
        if (s.length() != 81) {
            throw new IllegalArgumentException("Expected 81 chars");
        }
        Board b = new Board();
        for (int i = 0; i < 81; i++) {
            char ch = s.charAt(i);
            int r = i / 9, c = i % 9;
            if (ch >= '1' && ch <= '9') {
                b.setFixed(r, c, ch - '0');
            }
        }
        return b;
    }

    /**
     * Szövegképet készít a tábla aktuális értékeiből ponttal jelölve az üres cellákat.
     */
    public String exportToString(Board b) {
        StringBuilder sb = new StringBuilder(81);
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int v = b.get(r, c);
                sb.append(v == 0 ? '.' : (char) ('0' + v));
            }
        }
        return sb.toString();
    }

    /**
     * Fájlba írja az exportált szövegképet.
     */
    public void exportToFile(Board b, Path file) throws IOException {
        Files.writeString(file, exportToString(b));
    }

    /**
     * Beolvassa a fájlt és táblát készít belőle.
     */
    public Board importFromFile(Path file) throws IOException {
        String s = Files.readString(file);
        return importFromString(s);
    }
}
