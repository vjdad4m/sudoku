package com.vadam.sudoku.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.vadam.sudoku.model.Difficulty;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class StatisticsServiceTest {
    @TempDir
    Path tempDir;

    private Path statsFile;

    @BeforeEach
    void setUp() {
        statsFile = tempDir.resolve("stats.json");
    }

    @Test
    void recordGameCreatesNewEntryWithBestTime() {
        StatisticsService service = new StatisticsService(statsFile);

        assertThat(service.bestTime(Difficulty.EASY)).isEmpty();

        service.recordGame(Difficulty.EASY, 1_500L);

        assertThat(service.gameCount(Difficulty.EASY)).isEqualTo(1);
        assertThat(service.bestTime(Difficulty.EASY)).contains(1_500L);
    }

    @Test
    void recordGameOnlyReplacesBestWithFasterTimes() {
        StatisticsService service = new StatisticsService(statsFile);

        service.recordGame(Difficulty.MEDIUM, 2_000L);
        assertThat(service.bestTime(Difficulty.MEDIUM)).contains(2_000L);

        service.recordGame(Difficulty.MEDIUM, 2_500L);
        assertThat(service.bestTime(Difficulty.MEDIUM)).contains(2_000L);

        service.recordGame(Difficulty.MEDIUM, 1_800L);
        assertThat(service.gameCount(Difficulty.MEDIUM)).isEqualTo(3);
        assertThat(service.bestTime(Difficulty.MEDIUM)).contains(1_800L);
    }

    @Test
    void statisticsArePersistedAndReloadedFromDisk() {
        StatisticsService first = new StatisticsService(statsFile);
        first.recordGame(Difficulty.HARD, 5_000L);

        StatisticsService reloaded = new StatisticsService(statsFile);

        assertThat(reloaded.gameCount(Difficulty.HARD)).isEqualTo(1);
        assertThat(reloaded.bestTime(Difficulty.HARD)).contains(5_000L);
    }
}
