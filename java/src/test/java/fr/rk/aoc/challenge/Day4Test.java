package fr.rk.aoc.challenge;

import fr.rk.aoc.challenge.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
public class Day4Test {

    ArrayList<String> inputTest = new ArrayList<>(Arrays.asList("7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1",
            "",
            "22 13 17 11  0",
            " 8  2 23  4 24",
            "21  9 14 16  7",
            " 6 10  3 18  5",
            " 1 12 20 15 19",
            "",
            " 3 15  0  2 22",
            " 9 18 13 17  5",
            "19  8  7 25 23",
            "20 11 10 24  4",
            "14 21 16 12  6",
            "",
            "14 21 17 24  4",
            "10 16 15  9 19",
            "18  8 23 26 20",
            "22 11 13  6  5",
            " 2  0 12  3  7"));

    @Test
    public void testGetBingoWinnerGridFinalScoreOnRow() {
        Day4 day4 = new Day4();
        MatcherAssert.assertThat("Winner grid final score is correctly calculated", day4.getFirstBingoWinnerGridFinalScore(inputTest), Matchers.equalTo(4512L));
    }

    @Test
    public void testGetBingoWinnerGridFinalScoreOnCol() {
        ArrayList<String> inputTest = new ArrayList<>(Arrays.asList("7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1",
                "",
                " 7  5 20 19  4",
                "10 16 15  9 24",
                "18  8 23 26 17",
                "22 11 13  6 21",
                " 2  0 12  3 14"));
        Day4 day4 = new Day4();
        MatcherAssert.assertThat("Winner grid final score is correctly calculated", day4.getFirstBingoWinnerGridFinalScore(inputTest), Matchers.equalTo(4512L));
    }

    @Test
    public void testGetBingoLastWinnerGridFinalScore() {
        Day4 day4 = new Day4();
        MatcherAssert.assertThat("Last Winner grid final score is correctly calculated", day4.getLastBingoWinnerGridFinalScore(inputTest), Matchers.equalTo(1924L));
    }

    @Test
    public void getFirstChallengeResult() {
        Day4 day4 = new Day4();
        FileUtils.readInputFileAsList("input.txt", 4)
                .ifPresent(lines -> log.info(String.valueOf(day4.getFirstBingoWinnerGridFinalScore(lines))));
    }

    @Test
    public void getSecondChallengeResult() {
        Day4 day4 = new Day4();
        FileUtils.readInputFileAsList("input.txt", 4)
                .ifPresent(lines -> log.info(String.valueOf(day4.getLastBingoWinnerGridFinalScore(lines))));
    }
}
