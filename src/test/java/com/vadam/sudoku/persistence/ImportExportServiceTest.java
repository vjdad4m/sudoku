package com.vadam.sudoku.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.vadam.sudoku.model.Board;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ImportExportServiceTest {
    private final ImportExportService service = new ImportExportService();

    @TempDir
    Path tempDir;

    @Test
    void importFromStringParsesDigitsIntoFixedCells() {
        String puzzle = "123456789" + ".".repeat(72);

        Board board = service.importFromString(puzzle);

        assertThat(board.get(0, 0)).isEqualTo(1);
        assertThat(board.isFixed(0, 0)).isTrue();
        assertThat(board.get(0, 8)).isEqualTo(9);
        assertThat(board.get(1, 0)).isZero();
        assertThat(board.isFixed(1, 0)).isFalse();
    }

    @Test
    void exportToStringSerializesBoardState() {
        Board board = new Board();
        board.setValue(0, 0, 5);
        board.setFixed(0, 1, 8);
        board.setValue(8, 8, 9);

        String exported = service.exportToString(board);

        assertThat(exported).hasSize(81);
        assertThat(exported.charAt(0)).isEqualTo('5');
        assertThat(exported.charAt(1)).isEqualTo('8');
        assertThat(exported.charAt(80)).isEqualTo('9');
        assertThat(exported.chars().filter(ch -> ch == '.').count()).isEqualTo(78);
    }

    @Test
    void importRejectsStringsWithInvalidLength() {
        assertThatThrownBy(() -> service.importFromString("123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("81");
    }

    @Test
    void exportAndImportViaFileRoundTripBoardState() throws IOException {
        Board board = new Board();
        board.setFixed(0, 0, 7);
        board.setFixed(4, 4, 5);
        board.setFixed(8, 8, 3);

        Path file = tempDir.resolve("puzzle.txt");
        service.exportToFile(board, file);

        Board reloaded = service.importFromFile(file);

        assertThat(reloaded.get(0, 0)).isEqualTo(7);
        assertThat(reloaded.isFixed(0, 0)).isTrue();
        assertThat(reloaded.get(4, 4)).isEqualTo(5);
        assertThat(reloaded.get(8, 8)).isEqualTo(3);
    }
}
