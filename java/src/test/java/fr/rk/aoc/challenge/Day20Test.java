package fr.rk.aoc.challenge;

import fr.rk.aoc.challenge.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
public class Day20Test {

    ArrayList<String> inputTest = new ArrayList<>(Arrays.asList(
            "..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#",
            "",
            "#..#.",
            "#....",
            "##..#",
            "..#..",
            "..###"));

    @Test
    public void getLitPixelAfterTwoEnhancement() {
        MatcherAssert.assertThat(Day20.getLitPixelAfterNEnhancement(inputTest, 2), Matchers.equalTo(35L));
    }

    @Test
    public void getLitPixelAfterFifryEnhancement() {
        MatcherAssert.assertThat(Day20.getLitPixelAfterNEnhancement(inputTest, 50), Matchers.equalTo(3351L));
    }

    @Test
    public void getFirstChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 20)
                .ifPresent(lines -> log.info(String.valueOf(Day20.getLitPixelAfterNEnhancement(lines, 2))));
    }

    @Test
    public void getSecondChallengeResult() {
        FileUtils.readInputFileAsList("input.txt", 20)
                .ifPresent(lines -> log.info(String.valueOf(Day20.getLitPixelAfterNEnhancement(lines, 50))));
    }

}
