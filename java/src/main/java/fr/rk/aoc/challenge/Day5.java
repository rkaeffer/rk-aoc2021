package fr.rk.aoc.challenge;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Day5 {

    public long calculateOverlappingWind(List<String> input, boolean isFirstChallenge) {
        List<Segment> segments = this.parseWindGrid(input, isFirstChallenge);
        segments.forEach(s -> log.info(s.toString()));
        int maxX = 0;
        int maxY = 0;
        for(Segment s : segments) {
            maxX = Math.max(s.getStart().getX(), maxX);
            maxX = Math.max(s.getEnd().getX(), maxX);
            maxY = Math.max(s.getStart().getY(), maxY);
            maxY = Math.max(s.getEnd().getY(), maxY);

        }
        WindGrid windGrid = new WindGrid(segments,maxY,maxX);
        log.info(windGrid.toString());
        windGrid.calculateCrossOver();
        System.out.println();
        log.info(windGrid.toString());
        return windGrid.getNumberOfDangerousPosition();
    }

    private List<Segment> parseWindGrid(List<String> windInput, boolean isFirstChallenge) {
        return windInput.stream()
                .map(s -> s.split("->"))
                .map(point ->
                    new Segment(
                        new Point(Integer.parseInt(point[0].trim().split(",")[0]),
                                Integer.parseInt(point[0].trim().split(",")[1])
                        ),
                        new Point(Integer.parseInt(point[1].trim().split(",")[0]),
                                Integer.parseInt(point[1].trim().split(",")[1])
                        )
                    )
                )
                .filter(isFirstChallenge ? segment -> segment.getStart().getX() == segment.getEnd().getX() || segment.getStart().getY() == segment.getEnd().getY() : segment -> true)
                .collect(Collectors.toList());
    }
    
    public class WindGrid {
        List<Segment> segments;
        int[][] windGrid;

        public WindGrid(List<Segment> segments, int nbCol, int nbLine) {
            this.segments = segments;
            this.windGrid = new int[nbCol + 1][nbLine + 1];
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(System.lineSeparator());
            for (int[] ints : this.windGrid) {
                for (int anInt : ints) {
                    sb.append(anInt);
                }
                sb.append(System.lineSeparator());
            }
            return sb.toString();
        }

        public void calculateCrossOver() {
            for(Segment segment : segments) {
                for(Point p : segment.getAllSegmentsPoint()) {
                    this.windGrid[p.getY()][p.getX()]++;
                }
            }
        }

        public long getNumberOfDangerousPosition() {
            long dangerousPosition = 0;
            for (int[] ints : this.windGrid) {
                for (int anInt : ints) {
                    if (anInt >= 2) {
                        dangerousPosition++;
                    }
                }
            }
            return dangerousPosition;
        }
    }

    @Getter
    @Setter
    public class Segment {

        private Point start;
        private Point end;

        private List<Point> allSegmentsPoint;

        public Segment(Point start, Point end) {
            this.start = start;
            this.end = end;
            this.allSegmentsPoint = new ArrayList<>();
            this.allSegmentsPoint.add(start);
            this.calculateAllPoints();
            this.allSegmentsPoint.add(end);
        }

        /**
         * Calculate each points in the line
         *
         * Must be review
         */
        private void calculateAllPoints() {
            if(start.getX() == end.getX()) {
                if(start.getY() > end.getY()) {
                    for(int i=end.getY() + 1; i< start.getY(); i++) {
                        this.allSegmentsPoint.add(new Point(start.getX(), i));
                    }
                } else {
                    for(int i=start.getY() + 1; i< end.getY(); i++) {
                        this.allSegmentsPoint.add(new Point(start.getX(), i));
                    }
                }
            } else if(start.getY() == end.getY()){
                if(start.getX() > end.getX()) {
                    for(int i=end.getX() + 1; i< start.getX(); i++) {
                        this.allSegmentsPoint.add(new Point(i, start.getY()));
                    }
                } else {
                    for(int i=start.getX() + 1; i< end.getX(); i++) {
                        this.allSegmentsPoint.add(new Point(i, start.getY()));
                    }
                }
            } else {
                int xDiag = start.getX();
                int yDiag = start.getY();
                if(start.getX() < end.getX() && start.getY() < end.getY()) {
                    for(int i=start.getX() + 1; i<end.getX(); i++) {
                        xDiag++;
                        yDiag++;
                        this.allSegmentsPoint.add(new Point(xDiag, yDiag));
                    }
                } else if (start.getX() > end.getX() && start.getY() > end.getY()) {
                    for(int i=start.getX() - 1; i>end.getX(); i--) {
                        xDiag--;
                        yDiag--;
                        this.allSegmentsPoint.add(new Point(xDiag, yDiag));
                    }
                } else if (start.getX() < end.getX() && start.getY() > end.getY()) {
                    for(int i=start.getX() + 1; i<end.getX(); i++) {
                        xDiag++;
                        yDiag--;
                        this.allSegmentsPoint.add(new Point(xDiag, yDiag));
                    }
                } else if (start.getX() > end.getX() && start.getY() < end.getY()) {
                    for(int i=start.getX() - 1; i>end.getX(); i--) {
                        xDiag--;
                        yDiag++;
                        this.allSegmentsPoint.add(new Point(xDiag, yDiag));
                    }
                }
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(System.lineSeparator());
            for(Point p : allSegmentsPoint) {
                sb.append("[").append(p.getX()).append(",").append(p.getY()).append("],");
            }
            sb.append(System.lineSeparator());
            return sb.toString();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class Point {
        private int x;
        private int y;

        public boolean equals(Point other) {
            return other.getX() == this.getX() && other.getY() == this.getY();
        }
    }
}
