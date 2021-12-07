package fr.rk.aoc.challenge;

import java.util.Arrays;

public final class Day7 {

    /**
     * Calculate minimal fuel necessary to align crab considering each crab move cost 1 fuel
     *
     * @param input initial crabs position
     * @return minimal fuel consumption to align all crabs
     */
    public static long calculateMinimalFuelToAlignCrab(int[] input) {
        long[] fuel = new long[input.length];
        for (int i = 0; i < input.length; i++) {
            for (int crab : input) {
                fuel[i] += Math.abs(crab - input[i]);
            }
        }
        return Arrays.stream(fuel).min().getAsLong();
    }

    /**
     * Calculate minimal fuel necessary to align crab considering each crab move cost 1 more fuel than last move, starting at 1 fuel for the first move
     *
     * @param input initial crabs position
     * @return minimal fuel consumption to align all crabs
     */
    public static long calculateMinimalFuelToAlignCrabWithFuelExpansion(int[] input) {
        long[] fuel = new long[input.length];
        for (int i = 0; i < input.length; i++) {
            for (int crab : input) {
                int nbPosChange = Math.abs(crab - i);
                //Sum from 1 to nbPosCHange using formula sum = n(n+1)/2
                fuel[i] += (nbPosChange * (nbPosChange + 1) / 2);
            }
        }
        return Arrays.stream(fuel).min().getAsLong();
    }
}
