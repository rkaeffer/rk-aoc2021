package fr.rk.aoc.challenge;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

public final class Day2 {

    public static long getSubmarineFinalPosition(List<String> submarinesMoves) {
        int xPos = 0;
        int yPos = 0;
        for(String submarinesMove : submarinesMoves) {
            String[] moveCommand = submarinesMove.split(" ");
            long moveDistance = Integer.parseInt(moveCommand[1]);
            switch (Objects.requireNonNull(SubmarineMoveDirection.getSubmarineMoveDirectionByDirection(moveCommand[0]))) {
                case UP:
                    yPos -= moveDistance;
                    break;
                case DOWN:
                    yPos += moveDistance;
                    break;
                case FORWARD:
                    xPos += moveDistance;
                    break;
                default:
                    break;
            }
        }
        return xPos * yPos;
    }


    @AllArgsConstructor
    @Getter
    public enum SubmarineMoveDirection {
        FORWARD("forward"),
        UP("up"),
        DOWN("down");

        private final String direction;

        public static SubmarineMoveDirection getSubmarineMoveDirectionByDirection(String direction) {
            for(SubmarineMoveDirection submarineMoveDirection : values()) {
                if(submarineMoveDirection.getDirection().equals(direction)) {
                    return submarineMoveDirection;
                }
            }
            return null;
        }
    }


}
