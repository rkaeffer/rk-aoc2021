package fr.rk.aoc.challenge;

import fr.rk.aoc.challenge.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
public class Day10Test {

    ArrayList<String> inputTest = new ArrayList<>(Arrays.asList(
            "[({(<(())[]>[[{[]{<()<>>", // Incomplete
            "[(()[<>])]({[<{<<[]>>(", // Incomplete
            "{([(<{}[<>[]}>{[]{[(<()>", // Corrupted
            "(((({<>}<{<{<>}{[]{[]{}", //Incomplete
            "[[<[([]))<([[{}[[()]]]", // Corrupted
            "[{[{({}]{}}([{[{{{}}([]", // Corrupted
            "{<[[]]>}<{[{[{[]{()[[[]", // Incomplete
            "[<(<(<(<{}))><([]([]()", // Corrupted
            "<{([([[(<>()){}]>(<<{{", // Incomplete
            "<{([{{}}[<[[[<>{}]]]>[]]")); //Corrupted

    @Test
    public void testGetCorruptedLinesScore() {
        MatcherAssert.assertThat("Corrupted score is correctly calculated", Day10.getCorruptedScore(inputTest), Matchers.equalTo(26397L));
    }

    @Test
    public void testGetIncompleteLinesScore() {
        MatcherAssert.assertThat("Corrupted score is correctly calculated", Day10.getIncompleteScore(inputTest), Matchers.equalTo(288957L));
    }

    @Test
    public void getFirstChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 10)
                .ifPresent(lines -> log.info(String.valueOf(Day10.getCorruptedScore(lines))));
    }

    @Test
    public void getSecondChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 10)
                .ifPresent(lines -> log.info(String.valueOf(Day10.getIncompleteScore(lines))));
    }
}
