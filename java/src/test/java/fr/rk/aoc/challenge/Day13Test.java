package fr.rk.aoc.challenge;

import fr.rk.aoc.challenge.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
public class Day13Test {

    ArrayList<String> inputTest = new ArrayList<>(Arrays.asList(
            "6,10",
            "0,14",
            "9,10",
            "0,3",
            "10,4",
            "4,11",
            "6,0",
            "6,12",
            "4,1",
            "0,13",
            "10,12",
            "3,4",
            "3,0",
            "8,4",
            "1,10",
            "2,14",
            "8,10",
            "9,0",
            "",
            "fold along y=7",
            "fold along x=5"));

    @Test
    public void testGetNumberOfVisiblePointAfterOneFolding() {
        MatcherAssert.assertThat("Number of point after folding is correctly calculated", Day13.getNumberOfVisiblePointAfterNFolding(inputTest,1), Matchers.equalTo(17L));
    }

    @Test
    public void testGetNumberOfVisiblePointAfterTwoFolding() {
        MatcherAssert.assertThat("Number of point after folding is correctly calculated", Day13.getNumberOfVisiblePointAfterNFolding(inputTest,2), Matchers.equalTo(16L));
    }

    @Test
    public void getFirstChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 13)
                .ifPresent(lines -> log.info(String.valueOf(Day13.getNumberOfVisiblePointAfterNFolding(lines,1))));
    }

    @Test
    public void getSecondChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 13)
                .ifPresent(lines -> log.info(String.valueOf(Day13.getNumberOfVisiblePointAfterNFolding(lines,-1))));
        //If you got bad eyes; result is : RCPLAKHL
    }

}
