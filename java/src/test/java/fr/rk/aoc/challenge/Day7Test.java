package fr.rk.aoc.challenge;

import fr.rk.aoc.challenge.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.*;

@Slf4j
public class Day7Test {

    ArrayList<String> inputTest = new ArrayList<>(Collections.singletonList("16,1,2,0,4,2,7,1,2,14"));

    @Test
    public void testCalculateMinimalFuelToAlignCrab(){
        int[] crabs = Arrays.stream(inputTest.get(0).split(",")).mapToInt(Integer::valueOf).toArray();
        MatcherAssert.assertThat("The minimal fuel to align all crabs is correctly calculated", Day7.calculateMinimalFuelToAlignCrab(crabs), Matchers.equalTo(37L));
    }

    @Test
    public void testCalculateMinimalFuelToAlignCrabWithFuelExpansion(){
        int[] crabs = Arrays.stream(inputTest.get(0).split(",")).mapToInt(Integer::valueOf).toArray();
        MatcherAssert.assertThat("The minimal fuel to align all crabs with fuel expansion is correctly calculated", Day7.calculateMinimalFuelToAlignCrabWithFuelExpansion(crabs), Matchers.equalTo(168L));
    }

    @Test
    public void getFirstChallengeResult() {
        Optional<List<String>> strings = FileUtils.readInputFileAsList("input.txt", 7);
        strings.ifPresent(stringList -> log.info(String.valueOf(
                Day7.calculateMinimalFuelToAlignCrab(
                        Arrays.stream(stringList.get(0).split(",")).mapToInt(Integer::valueOf).toArray())
                )
        ));
    }

    @Test
    public void getSecondChallengeResult() {
        Optional<List<String>> strings = FileUtils.readInputFileAsList("input.txt", 7);
        strings.ifPresent(stringList -> log.info(String.valueOf(
                Day7.calculateMinimalFuelToAlignCrabWithFuelExpansion(
                        Arrays.stream(stringList.get(0).split(",")).mapToInt(Integer::valueOf).toArray())
                )
        ));
    }


}
