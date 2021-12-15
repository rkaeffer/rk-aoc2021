package fr.rk.aoc.challenge;

import fr.rk.aoc.challenge.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
public class Day14Test {

    ArrayList<String> inputTest = new ArrayList<>(Arrays.asList(
            "NNCB",
            "",
            "CH -> B",
            "HH -> N",
            "CB -> H",
            "NH -> C",
            "HB -> C",
            "HC -> B",
            "HN -> C",
            "NN -> C",
            "BH -> H",
            "NC -> B",
            "NB -> B",
            "BN -> B",
            "BB -> N",
            "BC -> B",
            "CC -> N",
            "CN -> C"));

    @Test
    public void testGetMostCommonElementQuantityMinusMostCommonElement() {
        MatcherAssert.assertThat("Total is correctly calculated", Day14.getMostCommonElementQuantityMinusMostCommonElement(inputTest, 10), Matchers.equalTo(BigInteger.valueOf(1588L)));
    }

    @Test
    public void getFirtChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 14)
                .ifPresent(lines -> log.info(String.valueOf(Day14.getMostCommonElementQuantityMinusMostCommonElement(lines,10))));
    }

    @Test
    public void getSecondChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 14)
                .ifPresent(lines -> log.info(String.valueOf(Day14.getMostCommonElementQuantityMinusMostCommonElement(lines,40))));
    }

}
