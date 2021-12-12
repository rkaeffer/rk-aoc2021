package fr.rk.aoc.challenge;

import fr.rk.aoc.challenge.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
public class Day11Test {

    ArrayList<String> inputTestSimple = new ArrayList<>(Arrays.asList(
            "11111",
            "19991",
            "19191",
            "19991",
            "11111"));

    ArrayList<String> inputTestSComplex = new ArrayList<>(Arrays.asList(
            "5483143223",
            "2745854711",
            "5264556173",
            "6141336146",
            "6357385478",
            "4167524645",
            "2176841721",
            "6882881134",
            "4846848554",
            "5283751526"));

    @Test
    public void testCalculateNumberFlashesOnSimpleInputOneStep() {
        MatcherAssert.assertThat("Correctly calculate after 1 step", Day11.getNumberOfFlashesAfterNStep(inputTestSimple, 1), Matchers.equalTo(9L));
    }

    @Test
    public void testCalculateNumberFlashesOnSimpleInputTwoStep() {
        MatcherAssert.assertThat("Correctly calculate after 2 step", Day11.getNumberOfFlashesAfterNStep(inputTestSimple, 2), Matchers.equalTo(9L));
    }

    @Test
    public void testCalculateNumberFlashesOnSComplexInputTenStep() {
        MatcherAssert.assertThat("Correctly calculate after 10 step", Day11.getNumberOfFlashesAfterNStep(inputTestSComplex, 10), Matchers.equalTo(204L));
    }

    @Test
    public void testCalculateNumberFlashesOnSComplexInputOneHundredStep() {
        MatcherAssert.assertThat("Correctly calculate after 100 step", Day11.getNumberOfFlashesAfterNStep(inputTestSComplex, 100), Matchers.equalTo(1656L));
    }

    @Test
    public void testCalculateNbStepBeforeAllFlash() {
        MatcherAssert.assertThat("Step 195 is the first step where all octopuses flashes", Day11.getFirstStepWithAllFlash(inputTestSComplex), Matchers.equalTo(195L));
    }

     @Test
    public void getFirstChallengeResult() {
         FileUtils.readInputFileAsList("input.txt", 11)
                 .ifPresent(lines -> log.info(String.valueOf(Day11.getNumberOfFlashesAfterNStep(lines, 100))));
     }

    @Test
    public void getSecondChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 11)
                .ifPresent(lines -> log.info(String.valueOf(Day11.getFirstStepWithAllFlash(lines))));
    }

}
