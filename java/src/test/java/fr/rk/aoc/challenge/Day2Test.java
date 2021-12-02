package fr.rk.aoc.challenge;

import fr.rk.aoc.challenge.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
public class Day2Test {

    @Test
    public void testGetSubmarineFinalPosition() {
        ArrayList<String> inputTest = new ArrayList<>(Arrays.asList("forward 5","down 5","forward 8","up 3","down 8","forward 2"));
        MatcherAssert.assertThat("Submarine final position is correctly calculated", Day2.getSubmarineFinalPosition(inputTest), Matchers.equalTo(150L));
    }

    @Test
    public void getFirstChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 2)
                .ifPresent(lines -> log.info(String.valueOf(Day2.getSubmarineFinalPosition(lines))));
    }


}
