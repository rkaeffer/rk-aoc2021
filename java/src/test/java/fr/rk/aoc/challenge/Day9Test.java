package fr.rk.aoc.challenge;

import fr.rk.aoc.challenge.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
public class Day9Test {

    ArrayList<String> inputTest = new ArrayList<>(Arrays.asList(
            "2199943210",
            "3987894921",
            "9856789892",
            "8767896789",
            "9899965678"));

    @Test
    public void testGetLowPointRiskSum() {
        MatcherAssert.assertThat("Low points total risk is correctly calculated", Day9.getLowPointRiskSum(inputTest), Matchers.equalTo(15L));
    }

    @Test
    public void testGetThreeLargestBasin() {
        MatcherAssert.assertThat("Low points total risk is correctly calculated", Day9.getThreeLargestBasin(inputTest), Matchers.equalTo(1134L));
    }


    @Test
    public void getFirstChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 9)
                .ifPresent(lines -> log.info(String.valueOf(Day9.getLowPointRiskSum(lines))));
    }

    @Test
    public void getSecondChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 9)
                .ifPresent(lines -> log.info(String.valueOf(Day9.getThreeLargestBasin(lines))));
    }
}
