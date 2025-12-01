package com.vadam.sudoku.util;

public class ValidationUtils {
    /**
     * Segédosztály, nem példányosítható.
     */
    private ValidationUtils() {
    }

    /**
     * Ellenőrzi, hogy a tábla sorai, oszlopai és blokkjai nem tartalmaznak-e duplikációt vagy érvénytelen számot.
     */
    public static boolean isValid(int[][] v) {
        for (int r = 0; r < 9; r++) {
            boolean[] seen = new boolean[10];
            for (int c = 0; c < 9; c++) {
                int d = v[r][c];
                if (d != 0 && (seen[d] || d < 1 || d > 9))
                    return false;
                seen[d] = true;
            }
        }
        for (int c = 0; c < 9; c++) {
            boolean[] seen = new boolean[10];
            for (int r = 0; r < 9; r++) {
                int d = v[r][c];
                if (d != 0 && (seen[d] || d < 1 || d > 9))
                    return false;
                seen[d] = true;
            }
        }
        for (int br = 0; br < 9; br += 3) {
            for (int bc = 0; bc < 9; bc += 3) {
                boolean[] seen = new boolean[10];
                for (int r = br; r < br + 3; r++)
                    for (int c = bc; c < bc + 3; c++) {
                        int d = v[r][c];
                        if (d != 0 && (seen[d] || d < 1 || d > 9))
                            return false;
                        seen[d] = true;
                    }
            }
        }
        return true;
    }
}
