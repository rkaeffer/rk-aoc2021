package fr.rk.aoc.challenge;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day21Test {

    @Test
    public void testGetLosingPlayerScoreMultipleByNumberOfDieRoll() {
        MatcherAssert.assertThat(Day21.getLosingPlayerScoreMultipleByNumberOfDieRoll(4,8), Matchers.equalTo(739785L));
    }

    @Test
    public void testQuantumWinner() {
        MatcherAssert.assertThat(Day21.getUniverseWithMoreWinner(4,8), Matchers.equalTo(444356092776315L));
    }

    @Test
    public void getFirstChallengeResult() {
       log.info(String.valueOf(Day21.getLosingPlayerScoreMultipleByNumberOfDieRoll(8,1)));
    }

    @Test
    public void getSecondChallengeResult() {
        log.info(String.valueOf(Day21.getUniverseWithMoreWinner(8,1)));
    }
}
