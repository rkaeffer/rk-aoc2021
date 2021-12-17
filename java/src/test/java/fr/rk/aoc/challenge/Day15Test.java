package fr.rk.aoc.challenge;

import fr.rk.aoc.challenge.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
public class Day15Test {

    ArrayList<String> inputTest = new ArrayList<>(Arrays.asList(
            "1163751742",
            "1381373672",
            "2136511328",
            "3694931569",
            "7463417111",
            "1319128137",
            "1359912421",
            "3125421639",
            "1293138521",
            "2311944581"));

    @Test
    public void testGetShortestPathLength() {
        MatcherAssert.assertThat("", Day15.getShortestPathLength(inputTest, false), Matchers.equalTo(40L));
    }

    @Test
    public void testGetShortestPathLengthWithLargerDimension() {
        MatcherAssert.assertThat("", Day15.getShortestPathLength(inputTest, true), Matchers.equalTo(315L));
    }

    @Test
    public void getFirstChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 15)
                .ifPresent(lines -> log.info(String.valueOf(Day15.getShortestPathLength(lines, false))));
    }

    @Test
    public void getSecondChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 15)
                .ifPresent(lines -> log.info(String.valueOf(Day15.getShortestPathLength(lines, true))));
    }
}
