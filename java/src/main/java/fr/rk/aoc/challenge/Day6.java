package fr.rk.aoc.challenge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public final class Day6 {

    /**
     * Get fish population after N days
     *
     * @param fishInput initial population
     * @param nbDay number of day of the simulation
     * @return the fish population after the number of day of the simulation
     */
    public static long getNbFishAfterNDays(List<Integer> fishInput, int nbDay) {
        //Map representing our fish population
        Map<Integer, Long> fishMap = new HashMap<>();
        //Initialize map
        IntStream.range(0,9).forEach(index ->
                fishMap.put(index, fishInput.stream().filter(fish -> fish == index).count())
        );
        //Iterate over days
        IntStream.range(0, nbDay).forEach(day -> {
            Long nbNew = fishMap.get(0);
            //Switch every count of current day to day - 1
            IntStream.range(0,8).forEach(index -> {
                fishMap.put(index, fishMap.get(index +1));
            });
            //Add reset fish (Which have new born)
            fishMap.put(6, fishMap.get(6) + nbNew);
            //Add new born fish
            fishMap.put(8, nbNew);
        });
        return fishMap.values().stream().mapToLong(Long::valueOf).sum();
    }
}
