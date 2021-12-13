package fr.rk.aoc.challenge;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public final class Day13 {

    /**
     * Calculate number visible point after N foloding following folding instructions
     * Print result after N Folding
     *
     * @param input initial grid & folding instructions
     * @param nbFolding number of folding to apply
     * @return the number of non-empty point after nbFolding (And print final result)
     */
    public static long getNumberOfVisiblePointAfterNFolding(List<String> input, int nbFolding) {
        //Parse grid
        int[][] grid = parseInputGrid(input);
        //Parse instruction
        List<String> foldInstructions = parseFoldInstruction(input);
        //Folding
        for(int i = 0; i < (nbFolding == -1 ? foldInstructions.size() : nbFolding); i++) {
            grid = foldGrid(grid, foldInstructions.get(i));
        }
        //Print result
        Arrays.stream(grid).forEach(line -> log.info(Arrays.stream(line).mapToObj(i -> i > 0 ? "#" : ".").collect(Collectors.joining())));
        //Count non empty point
        return Arrays.stream(grid).map(line -> Arrays.stream(line).filter(v -> v > 0).count()).mapToLong(Long::valueOf).sum();
    }

    /**
     * Fold the grid
     *
     * @param grid current grid state
     * @param foldInstruction fold instruction to apply
     * @return the new grid after fold operation
     */
    private static int[][] foldGrid(int[][] grid, String foldInstruction) {
        int foldLimit = Integer.parseInt(foldInstruction.split("=")[1]);
        int[][] newGrid;
        //Case fold on Y
        if(foldInstruction.charAt(0) == 'y') {
            newGrid = new int[(grid.length - 1) / 2 ][grid[0].length];
            IntStream.range(foldLimit + 1, grid.length).forEach(line -> {
                IntStream.range(0, grid[line].length).forEach(column -> {
                    newGrid[foldLimit - (line - foldLimit)][column] = grid[line][column] + grid[foldLimit - (line - foldLimit)][column];
                });
            });
        //Case fold on X
        } else {
            newGrid = new int[grid.length][(grid[0].length - 1) / 2];
            IntStream.range(0, grid.length).forEach(line -> {
                IntStream.range(foldLimit + 1, grid[line].length).forEach(column -> {
                    newGrid[line][foldLimit - (column - foldLimit)] = grid[line][column] + grid[line][foldLimit - (column - foldLimit)];
                });
            });
        }
        return newGrid;
    }

    /**
     * Transform initial grid state end instruction to grid
     *
     * @param input List<String> with grid state and instruction
     * @return grid state as int[][]
     */
    private static int[][] parseInputGrid(List<String> input) {
        AtomicInteger maxX = new AtomicInteger(-1);
        AtomicInteger maxY = new AtomicInteger(-1);
        input.stream()
                .filter(line -> !line.startsWith("fold"))
                .filter(line -> line.length() > 0)
                .forEach(line -> {
                    String[] coordinates = line.split(",");
                    maxX.set(Math.max(maxX.get(), Integer.parseInt(coordinates[0])));
                    maxY.set(Math.max(maxY.get(), Integer.parseInt(coordinates[1])));
                });
        int[][] parsedInput = new int[maxY.get() + 1][maxX.get() + 1];
        log.info(String.valueOf(maxX.get()));
        log.info(String.valueOf(maxY.get()));
        input.stream()
                .filter(line -> !line.startsWith("fold"))
                .filter(line -> line.length() > 0)
                .forEach(line -> {
                    String[] coordinates = line.split(",");
                    parsedInput[Integer.parseInt(coordinates[1])][Integer.parseInt(coordinates[0])] = 1;
                });
        return parsedInput;
    }

    /**
     * Transform initial grid state and instructions to instructions
     *
     * @param input List<String> with grid state and instructions
     * @return instructions as List<String>
     */
    private static List<String> parseFoldInstruction(List<String> input) {
        List<String> foldInstructions = new ArrayList<>();
        input.stream().sequential()
                .filter(line -> line.startsWith("fold"))
                .forEach(line -> {
                    foldInstructions.add(line.split(" ")[2]);
                });
        return foldInstructions;
    }


}
