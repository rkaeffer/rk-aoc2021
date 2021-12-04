package fr.rk.aoc.challenge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day4 {

    /**
     * Get First Bingo grid winner score
     *
     * @param bingosInput list of all bingo's grid
     * @return first Bingo grid winner score
     */
    public long getFirstBingoWinnerGridFinalScore(List<String> bingosInput) {
        String[] inputNumber = bingosInput.get(0).split(",");
        List<BingoGrid> bingoGrids = this.parseBingoGrid(bingosInput);
        for(String nextNumb : inputNumber) {
            for(BingoGrid grid : bingoGrids) {
                grid.addMarkedNumberIfPresent(Integer.parseInt(nextNumb));
                if(grid.isWinner()) {
                    return grid.getUnmarkedScore() * Integer.parseInt(nextNumb);
                }
            }
        }
        return -1L;
    }

    /**
     * Get last Bingo grid winner score
     *
     * @param bingosInput list of all bingo's grid
     * @return last Bingo grid winner score
     */
    public long getLastBingoWinnerGridFinalScore(List<String> bingosInput) {
        String[] inputNumber = bingosInput.get(0).split(",");
        List<BingoGrid> bingoGrids = this.parseBingoGrid(bingosInput);
        int completedPos = 1;
        for(String nextNumb : inputNumber) {
            for(BingoGrid grid : bingoGrids) {
                if(!grid.completed) {
                    grid.addMarkedNumberIfPresent(Integer.parseInt(nextNumb));
                    if(grid.isWinner()) {
                        grid.setCompletedPos(completedPos);
                        grid.setWinnerNumb(Integer.parseInt(nextNumb));
                        completedPos++;
                    }
                }
            }
        }
        return bingoGrids.stream()
                .max(Comparator.comparing(BingoGrid::getCompletedPos))
                .map(bingoGrid -> bingoGrid.getUnmarkedScore() * bingoGrid.getWinnerNumb())
                .orElse(-1L);

    }

    /**
     * Method wich allows to contruct all bingo grid from input file
     *
     * @param bingosInput list of String from input file "as-is"
     * @return List of {@link BingoGrid} initialize
     */
    private List<BingoGrid> parseBingoGrid(List<String> bingosInput) {
        List<BingoGrid> bingoGrids = new ArrayList<>();
        int colIndex = 0;
        for(int i=1; i<bingosInput.size(); i++) {
            if(bingosInput.get(i).equals("")) {
                bingoGrids.add(new BingoGrid());
                colIndex = 0;
            } else {
                bingoGrids.get(bingoGrids.size() - 1).addLine(bingosInput.get(i), colIndex);
                colIndex++;
            }
        }
        return bingoGrids;
    }

    /**
     * Class representing a Bingo Grid
     */
    @Getter
    @Setter
    public class BingoGrid {

        BingoCell[][] bingoCells;
        private boolean completed;
        private int completedPos;
        private int winnerNumb;

        public BingoGrid() {
            this.bingoCells = new BingoCell[5][5];
            this.completed = false;
        }

        /**
         * Add a line of number to this grid (Initialisation)
         *
         * @param line the line to add (String format "as-is" in input file)
         * @param colIndex index of the ligne in the grid
         */
        public void addLine(String line, int colIndex) {
            String[] cells = line.split(" ");
            int rowIndex = 0;
            for(String cell : cells) {
                if(!cell.equals("")) {
                    this.bingoCells[colIndex][rowIndex] = new BingoCell(Integer.parseInt(cell), false);
                    rowIndex++;
                }
            }
        }

        /**
         * Get sum of unmarked number in this grid
         *
         * @return sum of unmarked number in this grid
         */
        public long getUnmarkedScore() {
            return Arrays.stream(bingoCells)
                    .map(line -> Arrays.stream(line)
                            .filter(cell -> !cell.isMarked())
                            .map(BingoCell::getValue)
                            .reduce(0, Integer::sum))
                    .reduce(0, Integer::sum);
        }

        /**
         * Marked drawn number as marked in list if present
         *
         * @param number current drawn number
         */
        public void addMarkedNumberIfPresent(int number) {
            for(BingoCell[] cells : this.bingoCells) {
                for(BingoCell cell : cells) {
                    if(cell.getValue() == number) {
                        cell.setMarked(true);
                    }
                }
            }
        }

        /**
         * Determine if this {@link BingoGrid} is winner
         *
         * @return true if grid has win, false otherwise
         */
        public boolean isWinner() {
            for(int i=0; i<bingoCells.length; i++) {
                boolean rowMarked = true;
                boolean colMarked = true;
                for(int j=0; j<bingoCells.length; j++) {
                    rowMarked &= bingoCells[i][j].isMarked();
                    colMarked &= bingoCells[j][i].isMarked();
                }
                if(rowMarked || colMarked) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Class representing a bingo cell
     */
    @Getter
    @Setter
    @AllArgsConstructor
    public class BingoCell {
        private int value;
        private boolean marked;
    }
}
