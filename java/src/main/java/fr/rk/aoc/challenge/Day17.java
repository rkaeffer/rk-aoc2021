package fr.rk.aoc.challenge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public final class Day17 {

    /**
     * Calculate the highest Y position a probe can reach when finishing in target among all launch that reach target
     *
     * @param input targetArea as String
     * @return the highest Y position a probe can reach when finishing in target among all launch that reach target
     */
    public static long getHighestYOfProbe(String input) {
        return simulateAllLaunch(parseTargerArea(input))
                .stream()
                .filter(ProbePath::isFinishInTargetArea)
                .map(path -> path.getProbePositions()
                        .stream()
                        .map(ProbePosition::getY)
                        .max(Long::compareTo))
                .map(l -> l.orElse(0L))
                .max(Long::compareTo)
                .orElse(-1L);
    }

    /**
     * Calculate the number of launch possibility that reach target
     *
     * @param input targetArea as String
     * @return the number of launch possibility that reach target
     */
    public static long getNumberOfProbeThatFinishInTargetArea(String input) {
        return simulateAllLaunch(parseTargerArea(input))
                .stream()
                .filter(ProbePath::isFinishInTargetArea)
                .count();
    }

    /**
     * Simulate all launch possibility to reach target area
     *
     * @param targetArea target Area of the probe
     * @return List of probe path each corresponding ta a launch possibility
     */
    private static List<ProbePath> simulateAllLaunch(TargetArea targetArea) {
        long xStart = targetArea.minX < 0 ? -1 : 1;
        final long xEnd = targetArea.maxX;
        long yStart = targetArea.minY;
        long yEnd = Math.abs(targetArea.minY);
        List<ProbePath> probePaths = new ArrayList<>();
        for(long x=xStart; xEnd < 0 ? x >= xEnd : x <= xEnd; x = xEnd < 0 ? x-1 : x+1) {
            for(long y=yStart; y <= yEnd; y++) {
                probePaths.add(calculateProbePath(x,y, targetArea));
            }
        }
        return probePaths;
    }

    /**
     * Calculate a probe path giving initial x velocity and y velocity
     *
     * @param xVelocity initial x velocity
     * @param yVelocity initial y velocity
     * @param targetArea target area
     * @return The path of the probe calculate until target area is reach or overpass
     */
    private static ProbePath calculateProbePath(long xVelocity, long yVelocity, TargetArea targetArea) {
        ProbePath probePath = new ProbePath();
        long curX = 0;
        long curY = 0;
        probePath.getProbePositions().add(new ProbePosition(0,0));
        while(!targetArea.hasOverpassTargetArea(curX, curY) && !targetArea.isInTargerArea(curX, curY)) {
            curX += xVelocity;
            curY += yVelocity;
            yVelocity--;
            if(xVelocity < 0) {
                xVelocity++;
            } else if(xVelocity > 0) {
                xVelocity--;
            }
            probePath.getProbePositions().add(new ProbePosition(curX,curY));
        }
        probePath.setFinishInTargetArea(targetArea.isInTargerArea(curX, curY));
        return probePath;

    }

    /**
     * Parse target area as String into {@link TargetArea}
     *
     * @param input target area as String
     * @return TargetArea parsed
     */
    private static TargetArea parseTargerArea(String input) {
        String[] positions = input.split(":")[1].split(",");
        String[] xPos = positions[0].split("=")[1].split("\\.\\.");
        String[] yPos = positions[1].split("=")[1].split("\\.\\.");
        TargetArea targetArea = new TargetArea();
        targetArea.setMinX(Long.parseLong(xPos[0].trim()));
        targetArea.setMaxX(Long.parseLong(xPos[1].trim()));
        targetArea.setMinY(Long.parseLong(yPos[0].trim()));
        targetArea.setMaxY(Long.parseLong(yPos[1].trim()));
        return targetArea;
    }

    /**
     * Class modeling a probe path
     */
    @Getter
    @Setter
    static class ProbePath {
        boolean finishInTargetArea;
        List<ProbePosition> probePositions = new ArrayList<>();
    }

    /**
     * Class modeling a probe position
     */
    @Getter
    @Setter
    @AllArgsConstructor
    static class ProbePosition {
        private long x;
        private long y;
    }

    /**
     * Class modeling a target area
     */
    @Getter
    @Setter
    static class TargetArea {
        private long maxX;
        private long minX;
        private long maxY;
        private long minY;

        /**
         * Check if a position is in target area
         *
         * @param x x position to test
         * @param y y position to test
         * @return true if (x,y) is in target area, false otherwise
         */
        public boolean isInTargerArea(long x, long y) {
            return x >= minX && x <= maxX && y >= minY && y <= maxY;
        }

        /**
         * Check if a position has overpass target area
         *
         * @param x x position to test
         * @param y y position to test
         * @return true if (x,y) has overpass target area, false otherwise
         */
        public boolean hasOverpassTargetArea(long x, long y) {
            boolean overpassX;
            if(minX < 0) {
                overpassX = x < minX;
            } else {
                overpassX = x > maxX;
            }
            boolean overpassY = y < minY;
            return overpassX || overpassY;
        }
    }

}
