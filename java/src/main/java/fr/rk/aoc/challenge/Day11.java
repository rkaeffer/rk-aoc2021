package fr.rk.aoc.challenge;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@Slf4j
public final class Day11 {

    /**
     * Get number of flashes of octopuses after N Step
     *
     * @param input octopuses initial state
     * @param nbStep Number of step that must be simulate
     * @return The number of flashes of octopuses in nbStep
     */
    public static long getNumberOfFlashesAfterNStep(List<String> input, int nbStep) {
        int[][] octopuses = parseInitialOctopusesState(input);
        AtomicLong flashes = new AtomicLong();
        IntStream.range(0, nbStep).sequential().forEach(step -> {
            // Octopuses simulation for ont step
            doOneOctopusesStep(octopuses);
            //Count flashes and reset to 0
            IntStream.range(0, octopuses.length).forEach(i -> {
                IntStream.range(0, octopuses[i].length).forEach(j -> {
                    if(octopuses[i][j] == -1) {
                        flashes.getAndIncrement();
                        octopuses[i][j] = 0;
                    }
                });
            });
        });
        return flashes.get();
    }

    /**
     * Identify the first step where all octopuses flash at same time
     *
     * @param input initial octopuses state
     * @return number of the first where all octopuses flashes
     */
    public static long getFirstStepWithAllFlash(List<String> input) {
        int[][] octopuses = parseInitialOctopusesState(input);
        AtomicBoolean allFlash = new AtomicBoolean(false);
        long step = 0;
        while(!allFlash.get()) {
            doOneOctopusesStep(octopuses);
            allFlash.set(true);
            IntStream.range(0, octopuses.length).forEach(i -> {
                IntStream.range(0, octopuses[i].length).forEach(j -> {
                    allFlash.set(allFlash.get() & octopuses[i][j] == -1);
                    if(octopuses[i][j] == -1) {
                        octopuses[i][j] = 0;
                    }
                });
            });
            step++;
        }
        return step;
    }

    /**
     * Simulate one step of octopuses flash
     *
     * @param octopuses current octopuses state
     */
    private static void doOneOctopusesStep(int[][] octopuses) {
        //First we increment each octopuses energy by 1
        IntStream.range(0, octopuses.length).sequential().forEach(i -> {
            IntStream.range(0, octopuses[i].length).sequential().forEach(j -> {
                octopuses[i][j]++;
            });
        });

        //Second, we diffuse flashes as much as needed, marking flashes cell as -1
        boolean mustDiffuse = true;
        while(mustDiffuse) {
            AtomicBoolean hasFlash = new AtomicBoolean(false);
            IntStream.range(0, octopuses.length).sequential().forEach(i -> {
                IntStream.range(0, octopuses[i].length).sequential().forEach(j -> {
                    if(octopuses[i][j] > 9) {
                        hasFlash.set(true);
                        octopuses[i][j] = - 1;
                        diffuseFlash(octopuses, i, j);
                    }
                });
            });
            mustDiffuse = hasFlash.get();
        }
    }

    /**
     * Diffuse flash of an octopus to neighbour octopus
     * @param octopuses current octopuses state
     * @param i y coordinate of octopus who has flash
     * @param j x coordinate of octopus who has flash
     */
    private static void diffuseFlash(int[][] octopuses, int i, int j) {
        if(i>0) {
            //Top right
            incrementCellIfNeeded(octopuses, i-1, j);
            if(j > 0) {
                incrementCellIfNeeded(octopuses, i-1, j-1);
            }
            if(j < octopuses[i].length - 1) {
                incrementCellIfNeeded(octopuses, i-1, j+1);
            }
            //Top Left
        }
        //Bottom
        if(i<octopuses.length - 1) {
            incrementCellIfNeeded(octopuses, i+1, j);
            //Bottom right
            if(j > 0) {
                incrementCellIfNeeded(octopuses, i+1, j-1);
            }
            //Bottom left
            if(j < octopuses[i].length - 1) {
                incrementCellIfNeeded(octopuses, i+1, j+1);
            }
        }
        //Left
        if(j > 0) {
            incrementCellIfNeeded(octopuses, i, j-1);
        }
        //Right
        if(j < octopuses[i].length - 1) {
            incrementCellIfNeeded(octopuses, i, j+1);
        }
    }

    /**
     * Increment octopus value if it has not flash during this step
     *
     * @param octopuses current octopuses state
     * @param i y coordinate of octopuses which must be increment
     * @param j x coordinate of octopuses which must be increment
     */
    private static void incrementCellIfNeeded(int[][] octopuses, int i, int j) {
        if(octopuses[i][j] != -1) {
            octopuses[i][j]++;
        }
    }

    /**
     * Transform initial octopuses state as int[][]
     *
     * @param input initial octopuses state as String
     * @return initial octopuses state as int[][]
     */
    private static int[][] parseInitialOctopusesState(List<String> input) {
        int[][] octopuses = new int[input.size()][input.get(0).length()];
        IntStream.range(0, input.size()).forEach(i -> {
            char[] lineChar = input.get(i).toCharArray();
            IntStream.range(0, lineChar.length).forEach(j -> {
                octopuses[i][j] = lineChar[j] - '0';
            });
        });
        return octopuses;
    }
}
