package fr.rk.aoc.challenge;

import fr.rk.aoc.challenge.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public final class Day6Test {

    ArrayList<String> inputTest = new ArrayList<>(Collections.singletonList("3,4,3,1,2"));

    @Test
    public void testGetNbFishAfter80Days(){
        List<Integer> inputAsInt = Arrays.stream(inputTest.get(0).split(",")).map(Integer::valueOf).collect(Collectors.toList());
        MatcherAssert.assertThat("Nb fish after 80 days is correcly calculated", Day6.getNbFishAfterNDays(inputAsInt, 80), Matchers.equalTo(5934L));
    }

    @Test
    public void testGetNbFishAfter256Days(){
        List<Integer> inputAsInt = Arrays.stream(inputTest.get(0).split(",")).map(Integer::valueOf).collect(Collectors.toList());
        MatcherAssert.assertThat("Nb fish after 80 days is correcly calculated", Day6.getNbFishAfterNDays(inputAsInt, 256), Matchers.equalTo(26984457539L));
    }

    @Test
    public void getFirstChallengeResult() {
        Optional<List<String>> strings = FileUtils.readInputFileAsList("input.txt", 6);
        strings.ifPresent(stringList -> log.info(String.valueOf(Day6.getNbFishAfterNDays(Arrays.stream(stringList.get(0).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList()), 80))));
    }

    @Test
    public void getSecondChallengeResult() {
        Optional<List<String>> strings = FileUtils.readInputFileAsList("input.txt", 6);
        strings.ifPresent(stringList -> log.info(String.valueOf(Day6.getNbFishAfterNDays(Arrays.stream(stringList.get(0).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList()), 256))));
    }
}
