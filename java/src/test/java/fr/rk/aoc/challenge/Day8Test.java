package fr.rk.aoc.challenge;

import fr.rk.aoc.challenge.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
public class Day8Test {


    ArrayList<String> inputTest = new ArrayList<>(Arrays.asList(
            "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe",
            "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc",
            "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg",
            "fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb",
            "aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea",
            "fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb",
            "dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe",
            "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef",
            "egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb",
            "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce"));

    ArrayList<String> inputTest2 = new ArrayList<>(Arrays.asList("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"));

    @Test
    public void testCountSimpleDigitInOutput() {
        MatcherAssert.assertThat("Simple digit in output is correctly calculated", Day8.countSimpleDigitInOutput(inputTest), Matchers.equalTo(26L));
    }

    @Test
    public void testCalculateSumOfOutputDigits() {
        MatcherAssert.assertThat("Sum of output digit is correctly calculated", Day8.calculateSumOfOutputDigits(inputTest2), Matchers.equalTo(5353L));
    }

    @Test
    public void testCalculateSumOfOutputDigitsAllEntry() {
        MatcherAssert.assertThat("Sum of output digit is correctly calculated", Day8.calculateSumOfOutputDigits(inputTest), Matchers.equalTo(61229L));
    }

    @Test
    public void getFirstChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 8)
                .ifPresent(lines -> log.info(String.valueOf(Day8.countSimpleDigitInOutput(lines))));
    }

    @Test
    public void getSecondChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 8)
                .ifPresent(lines -> log.info(String.valueOf(Day8.calculateSumOfOutputDigits(lines))));
    }
}
