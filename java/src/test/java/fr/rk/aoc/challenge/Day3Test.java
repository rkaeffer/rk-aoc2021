package fr.rk.aoc.challenge;

import fr.rk.aoc.challenge.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
public class Day3Test {

    @Test
    public void testGetSubmarineConsumption() {
        ArrayList<String> inputTest = new ArrayList<>(Arrays.asList("00100","11110","10110","10111","10101","01111","00111","11100","10000","11001","00010","01010"));
        MatcherAssert.assertThat("Submarine consumption is correctly calculated", Day3.calculateSubmarineConsumption(inputTest), Matchers.equalTo(198L));
    }

    @Test
    public void getFirstChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 3)
                .ifPresent(lines -> log.info(String.valueOf(Day3.calculateSubmarineConsumption(lines))));
    }

    @Test
    public void testGetSubmarineLifeSupport() {
        ArrayList<String> inputTest = new ArrayList<>(Arrays.asList("00100","11110","10110","10111","10101","01111","00111","11100","10000","11001","00010","01010"));
        MatcherAssert.assertThat("Submarine consumption is correctly calculated", Day3.calculateSubmarineLifeSupport(inputTest), Matchers.equalTo(230L));
    }

    @Test
    public void getSecondChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 3)
                .ifPresent(lines -> log.info(String.valueOf(Day3.calculateSubmarineLifeSupport(lines))));
    }
}
