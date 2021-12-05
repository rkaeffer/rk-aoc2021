package fr.rk.aoc.challenge;

import fr.rk.aoc.challenge.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
public class Day5Test {

    ArrayList<String> inputTest = new ArrayList<>(Arrays.asList(
            "0,9 -> 5,9",
            "8,0 -> 0,8",
            "9,4 -> 3,4",
            "2,2 -> 2,1",
            "7,0 -> 7,4",
            "6,4 -> 2,0",
            "0,9 -> 2,9",
            "3,4 -> 1,4",
            "0,0 -> 8,8",
            "5,5 -> 8,2"));

    @Test
    public void testGetOVerlappingWindIsCorrectlyCalculated() {
        Day5 day5 = new Day5();
        MatcherAssert.assertThat("Winner grid final score is correctly calculated", day5.calculateOverlappingWind(inputTest, true), Matchers.equalTo(5L));
        MatcherAssert.assertThat("Winner grid final score is correctly calculated", day5.calculateOverlappingWind(inputTest, false), Matchers.equalTo(12L));

    }

    @Test
    public void getFirstChallengeResult() {
        Day5 day5 = new Day5();
        FileUtils.readInputFileAsList("input.txt", 5)
                .ifPresent(lines -> log.info(String.valueOf(day5.calculateOverlappingWind(lines, true))));
    }

    @Test
    public void getSecondChallengeResult() {
        Day5 day5 = new Day5();
        FileUtils.readInputFileAsList("input.txt", 5)
                .ifPresent(lines -> log.info(String.valueOf(day5.calculateOverlappingWind(lines, false))));
    }
}
