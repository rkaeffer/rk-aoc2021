package fr.rk.aoc.challenge;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day1Test {

    @Test
    public void testNbTimesValuesIncreased() {
        ArrayList<Long> inputTest = new ArrayList<>(Arrays.asList(199L,200L,208L,210L,200L,207L,240L,269L,260L,263L));
        MatcherAssert.assertThat("Numbers of times where values increased in list is 7", Day1.nbTimesValuesIncreased(inputTest), Matchers.equalTo(7L));
    }

    @Test
    public void testNbTimesSumValuesIncreased() {
        ArrayList<Long> inputTest = new ArrayList<>(Arrays.asList(199L,200L,208L,210L,200L,207L,240L,269L,260L,263L));
        MatcherAssert.assertThat("Numbers of times where sum values increased in list is 5", Day1.nbTimesSumValuesIncreased(inputTest), Matchers.equalTo(5L));
    }


    @Test
    public void getFirstChallengeResult() {
        try (Stream<String> lines = Files.lines(Paths.get("src/test/resources/day1/firstChall/input.txt"))) {
            log.info(String.valueOf(Day1.nbTimesValuesIncreased(lines.map(Long::valueOf).collect(Collectors.toList()))));
        } catch (IOException ioException) {
            log.error("Unable to read input file : {}", ioException.getMessage());
        }
    }

    @Test
    public void getSecondChallengeResult() {
        try (Stream<String> lines = Files.lines(Paths.get("src/test/resources/day1/firstChall/input.txt"))) {
            log.info(String.valueOf(Day1.nbTimesSumValuesIncreased(lines.map(Long::valueOf).collect(Collectors.toList()))));
        } catch (IOException ioException) {
            log.error("Unable to read input file : {}", ioException.getMessage());
        }
    }
}
