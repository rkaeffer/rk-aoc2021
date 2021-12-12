package fr.rk.aoc.challenge;

import fr.rk.aoc.challenge.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
public class Day12Test {

    ArrayList<String> inputTestSimple = new ArrayList<>(Arrays.asList(
            "start-A",
            "start-b",
            "A-c",
            "A-b",
            "b-d",
            "A-end",
            "b-end"));

    ArrayList<String> inputTestMedium = new ArrayList<>(Arrays.asList(
            "dc-end",
            "HN-start",
            "start-kj",
            "dc-start",
            "dc-HN",
            "LN-dc",
            "HN-end",
            "kj-sa",
            "kj-HN",
            "kj-dc"));

    ArrayList<String> inputTesHard = new ArrayList<>(Arrays.asList(
            "fs-end",
            "he-DX",
            "fs-he",
            "start-DX",
            "pj-DX",
            "end-zg",
            "zg-sl",
            "zg-pj",
            "pj-he",
            "RW-he",
            "fs-DX",
            "pj-RW",
            "zg-RW",
            "start-pj",
            "he-WI",
            "zg-he",
            "pj-fs",
            "start-RW"));

    @Test
    public void testGetNbPathWhenCanVisitSmallCaveOnceSimpleInput() {
        MatcherAssert.assertThat("test", Day12.getNumberOfPath(inputTestSimple, false), Matchers.equalTo(10L));
    }

    @Test
    public void testGetNbPathWhenCanVisitSmallCaveOnceMediumInput() {
        MatcherAssert.assertThat("test", Day12.getNumberOfPath(inputTestMedium, false), Matchers.equalTo(19L));
    }

    @Test
    public void testGetNbPathWhenCanVisitSmallCaveOnceHardInput() {
        MatcherAssert.assertThat("test", Day12.getNumberOfPath(inputTesHard, false), Matchers.equalTo(226L));
    }

    @Test
    public void testGetNbPathWhenCanVisitSmallCaveTwiceSimpleInput() {
        MatcherAssert.assertThat("test", Day12.getNumberOfPath(inputTestSimple, true), Matchers.equalTo(36L));
    }

    @Test
    public void testGetNbPathWhenCanVisitSmallCaveTwiceMediumInput() {
        MatcherAssert.assertThat("test", Day12.getNumberOfPath(inputTestMedium, true), Matchers.equalTo(103L));
    }

    @Test
    public void testGetNbPathWhenCanVisitSmallCaveTwiceHardInput() {
        MatcherAssert.assertThat("test", Day12.getNumberOfPath(inputTesHard, true), Matchers.equalTo(3509L));
    }

    @Test
    public void getFirstChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 12)
                .ifPresent(lines -> log.info(String.valueOf(Day12.getNumberOfPath(lines, false))));
    }

    @Test
    public void getSecondChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 12)
                .ifPresent(lines -> log.info(String.valueOf(Day12.getNumberOfPath(lines, true))));
    }
}
