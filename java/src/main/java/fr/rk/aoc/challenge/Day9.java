package fr.rk.aoc.challenge;

import lombok.Data;

import java.util.*;
import java.util.stream.IntStream;

public final class Day9 {

    /**
     * Return the total low point risk
     *
     * @param input list of all point scan by submarine
     * @return the total of low point risk
     */
    public static long getLowPointRiskSum(List<String> input) {
        return evaluateLowPointRisk(initHeightMap(input), new ArrayList<>());
    }

    public static long getThreeLargestBasin(List<String> input) {
        int[][] heightmap = initHeightMap(input);
        //Evaluate low Point and init basins
        List<Basin> basins = new ArrayList<>();
        evaluateLowPointRisk(heightmap, basins);
        basins.forEach(basin -> {
            basin.setCnt(getBasinSize(basin.getI(), basin.getJ(), heightmap, null));
        });
        Collections.sort(basins);
        return basins.get(0).getCnt() *  basins.get(1).getCnt() *  basins.get(2).getCnt();
    }

    /**
     * Parse submarine input as int[][]
     *
     * @param input submarine input
     * @return submarine input parsed
     */
    private static int[][] initHeightMap(List<String> input) {
        int[][] heightmap = new int[input.size()][input.get(0).length()];
        IntStream.range(0, input.size()).forEach(i -> {
            char[] lineChar = input.get(i).toCharArray();
            IntStream.range(0, lineChar.length).forEach(j -> {
                heightmap[i][j] = lineChar[j] - '0';
            });
        });
        return heightmap;
    }

    /**
     * Calculate basin size
     *
     * @param i Basin start point y coordinate
     * @param j Basin start point x coordinate
     * @param heightMap submarine input
     * @param isPresent map containing current basin point identify (Must be null when this method is call outside from itself)
     * @return the basin size
     */
    private static long getBasinSize(int i, int j, int[][] heightMap, boolean[][] isPresent) {
        long cnt = 1;
        if(isPresent == null) {
            isPresent = new boolean[heightMap.length][heightMap[0].length];
            isPresent[i][j] = true;
        }

        //Left case
        if(j > 0 && heightMap[i][j - 1] != 9 && heightMap[i][j - 1] > heightMap[i][j] && !isPresent[i][j - 1]) {
            isPresent[i][j - 1] = true;
            cnt += getBasinSize(i, j - 1, heightMap, isPresent);
        }
        //Right case
        if(j < heightMap[0].length - 1  && heightMap[i][j + 1] != 9 && heightMap[i][j+1] > heightMap[i][j] && !isPresent[i][j + 1]) {
            isPresent[i][j + 1] = true;
            cnt += getBasinSize(i, j + 1, heightMap, isPresent);
        }
        //Top case
        if(i > 0 && heightMap[i - 1][j] != 9 && heightMap[i - 1][j] > heightMap[i][j] && !isPresent[i - 1][j]) {
            isPresent[i - 1][j] = true;
            cnt += getBasinSize(i - 1, j, heightMap, isPresent);
        }
        //Bottom case
        if(i < heightMap.length - 1 && heightMap[i + 1][j] != 9 && heightMap[i + 1][j] > heightMap[i][j] && !isPresent[i + 1][j]) {
            isPresent[i + 1][j] = true;
            cnt += getBasinSize(i + 1, j, heightMap, isPresent);
        }
        return cnt;
    }

    /**
     * Return the total low point risk, and init basin position
     *
     * @param heightmap list of all point scan by submarine parsed as int[][]
     * @param basins list of basin low point (start point for each basin)
     *
     * @return the total of low point risk, and init basin low point
     */
    private static long evaluateLowPointRisk(int[][] heightmap, List<Basin> basins) {
        long risk = 0;
        for (int i = 0; i < heightmap.length; i++) {
            for (int j = 0; j < heightmap[i].length; j++) {
                boolean lowPoint = j <= 0 || heightmap[i][j - 1] > heightmap[i][j];
                lowPoint &= j >= heightmap[0].length - 1 || heightmap[i][j + 1] > heightmap[i][j];
                lowPoint &= i <= 0 || heightmap[i - 1][j] > heightmap[i][j];
                lowPoint &= i >= heightmap.length - 1 || heightmap[i + 1][j] > heightmap[i][j];
                if(lowPoint) {
                    risk += 1 + heightmap[i][j] ;
                    basins.add(new Basin(i, j));
                }
            }
        }
        return risk;
    }

    /**
     * Basin class
     *
     * Represent a Basin
     */
    @Data
    private static class Basin implements Comparable<Basin> {
        private int i;
        private int j;
        private long cnt;

        public Basin(int i, int j) {
            this.i = i;
            this.j = j;
            this.cnt = 0;
        }

        @Override
        public int compareTo(Basin o) {
            return Long.compare(o.getCnt(), this.getCnt());
        }
    }

}
