package fr.rk.aoc.challenge;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public final class Day21 {

    /**
     * Calculate the losing player score of a Dirac Dive game multiply by the number where dices where roll during the game
     *
     * @param player1Position initial player 1 position
     * @param player2Position initial player 2 position
     * @return  the losing player score of a Dirac Dive game multiply by the number where dices where roll during the game
     */
    public static long getLosingPlayerScoreMultipleByNumberOfDieRoll(int player1Position, int player2Position) {
        Player player1 = new Player(player1Position, 0L);
        Player player2 = new Player(player2Position, 0L);
        Die die = new Die(100, 1, 0);
        Game game = new Game(die, true, player1, player2, 1000);
        while(!game.hasAWinner()) {
            if (game.player1turn) {
                game.playPlayer(game.player1);
                game.player1turn = false;
            } else {
                game.playPlayer(game.player2);
                game.player1turn = true;
            }
        }
        log.info(String.valueOf(die.nbRoll));
        log.info(String.valueOf(game.getLosingPLayer().score));
        return game.getLosingPLayer().score * game.die.nbRoll;
    }

    /**
     * Calculate universe with more winner
     * Totally rebuilt logic compare to challenge 1
     *
     * @param player1Position initial player 1 position
     * @param player2Position initial player 2 position
     * @return the number of universe where one of player have the max wins
     */
    public static long getUniverseWithMoreWinner(int player1Position, int player2Position) {
        //Possibility of sum are the following
        // 3 => 1/27
        // 4 => 3/27
        // 5 => 6/27
        // 6 => 7/27
        // 7 => 6/27
        // 8 => 3/27
        // 9 => 1/27
        List<DiePossibility> diePossibilities = new ArrayList<>();
        diePossibilities.add(new DiePossibility(3,1));
        diePossibilities.add(new DiePossibility(4,3));
        diePossibilities.add(new DiePossibility(5,6));
        diePossibilities.add(new DiePossibility(6,7));
        diePossibilities.add(new DiePossibility(7,6));
        diePossibilities.add(new DiePossibility(8,3));
        diePossibilities.add(new DiePossibility(9,1));
        QuantumGameState state = new QuantumGameState();
        for(DiePossibility diePossibility : diePossibilities) {
            QuantumGameState possibility = calculateQuantumWinners(player1Position, player2Position, 0, 0, true, diePossibility.sum, diePossibility.probability, diePossibilities);
            state.player1Wins += possibility.player1Wins;
            state.player2Wins += possibility.player2Wins;
        }
        log.info("Player 1 final : {}", state.player1Wins);
        log.info("Player 2 final : {}", state.player2Wins);
        return Math.max(state.player2Wins, state.player1Wins);
    }

    /**
     * Recursive calculation of universe where each player wins
     *
     * @param player1Position current player 1 position
     * @param player2Position current player 1 position
     * @param player1Score current player 1 score
     * @param player2Score current player 2 score
     * @param player1Turn true if it's player 1 turn, false otherwise
     * @param dieSum sum of three dices for current dices launch
     * @param dieProbability probability to get the dieSum with three dices launch
     * @param references dies possibility references
     * @return number of universe where players win
     */
    private static QuantumGameState calculateQuantumWinners(int player1Position, int player2Position, int player1Score, int player2Score, boolean player1Turn, long dieSum, long dieProbability, List<DiePossibility> references) {
        QuantumGameState quantumGameState = new QuantumGameState();
        if(player1Turn) {
            player1Position += dieSum;
            if(player1Position > 10) {
                player1Position -= 10;
            }
            player1Score += player1Position;
            if(player1Score >= 21) {
                quantumGameState.player1Wins+=dieProbability;
                return quantumGameState;
            }
        } else {
            player2Position += dieSum;
            if(player2Position > 10) {
                player2Position -= 10;
            }
            player2Score += player2Position;
            if(player2Score >= 21) {
                quantumGameState.player2Wins+=dieProbability;
                return quantumGameState;
            }
        }
        for(DiePossibility newPossibility : references) {
            QuantumGameState next = calculateQuantumWinners(player1Position, player2Position, player1Score, player2Score, !player1Turn, newPossibility.sum, dieProbability*newPossibility.probability,  references);
            quantumGameState.player1Wins += (next.player1Wins);
            quantumGameState.player2Wins += (next.player2Wins);
        }
        return quantumGameState;
    }

    /**
     * Class which represent a die possibility (Quantum part)
     */
    @AllArgsConstructor
    static class DiePossibility {
        public int sum;
        public int probability;
    }

    /**
     * count of universe where each player win (Quantum part)
     */
    static class QuantumGameState {
        public long player1Wins = 0L;
        public long player2Wins = 0L;
    }

    /**
     * Class that represent a Dirac Dice game
     */
    @AllArgsConstructor
    static class Game {
        Die die;
        boolean player1turn;
        Player player1;
        Player player2;
        int winScore;

        public boolean hasAWinner() {
            return player1.score >= winScore || player2.score >= winScore;
        }

        /**
         * PLay one turn of a player
         *
         * @param player player to play
         */
        public void playPlayer(Player player) {
            int move = (die.currentFaces) +
                    (die.currentFaces + 1 > die.facesNumber ? (die.currentFaces + 1)%die.facesNumber : die.currentFaces + 1) +
                    (die.currentFaces + 2 > die.facesNumber ? (die.currentFaces + 2)%die.facesNumber : die.currentFaces + 2);
            player.currentPosition += move;
            if(player.currentPosition%10 == 0) {
                player.score += 10;
                player.currentPosition = 10;
            } else {
                player.score += player.currentPosition%10;
                player.currentPosition = player.currentPosition%10;
            }
            die.currentFaces += 3;
            if(die.currentFaces > die.facesNumber) {
                die.currentFaces%=die.facesNumber;
            }
            if(die.currentFaces == 0) {
                die.currentFaces = 1;
            }
            die.nbRoll+=3;
        }

        /**
         * Determine the score of losing player
         *
         * @return the score of losing
         */
        public Player getLosingPLayer() {
            return player2.score > player1.score ? player1 : player2;
        }
    }

    /**
     * Class that represent a die
     */
    @AllArgsConstructor
    static class Die {
        private int facesNumber;
        private int currentFaces;
        private long nbRoll;
    }

    /**
     * Class that represent a player
     */
    @AllArgsConstructor
    static class Player {
        private int currentPosition;
        private long score;
    }

}
