package fr.rk.aoc.challenge;

import fr.rk.aoc.challenge.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day17Test {

    String inputTest="target area: x=20..30, y=-10..-5";

    @Test
    public void testGetHighestYOfProbe() {
        MatcherAssert.assertThat("Highest y is correctly calculated", Day17.getHighestYOfProbe(inputTest), Matchers.equalTo(45L));
    }

    @Test
    public void testGetNumberOfProbeThatFinishInTargetArea() {
        MatcherAssert.assertThat("Number of probes is correctly calculated", Day17.getNumberOfProbeThatFinishInTargetArea(inputTest), Matchers.equalTo(112L));
    }

    @Test
    public void getFirstChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 17)
                .ifPresent(lines -> log.info(String.valueOf(Day17.getHighestYOfProbe(lines.get(0)))));
    }

    @Test
    public void getSecondChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 17)
                .ifPresent(lines -> log.info(String.valueOf(Day17.getNumberOfProbeThatFinishInTargetArea(lines.get(0)))));
    }
}
