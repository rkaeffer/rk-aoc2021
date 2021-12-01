package fr.rk.aoc.challenge;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public final class Day1 {

    /**
     * Return the number of times a values increased comparing to previous one for a given list of Integer
     *
     * @param values input values
     * @return number of times a values increased comparing to previous one
     */
    public static long nbTimesValuesIncreased(List<Long> values) {
        return IntStream.range(0, values.size()-1)
                .reduce(0, (partial, current) -> partial + (values.get(current) > values.get(current + 1) ? 0 : 1));

    }

}
