package util;

import entity.Entity;

/**
 * Tracks the ongoing battle.
 */
public class GameBattle {
    private static final int WINS_REQUIRED = 2;
    private final EntityPool entityPool = new EntityPool();
    private GameMode gameMode;
    private GameCharacter playerOne;
    private GameCharacter playerTwo;
    private int playerOneWins = 0;
    private int playerTwoWins = 0;
    private String roundWinner;
    private String seriesWinner;
    private int roundNumber = 1;

    /**
     * Records a round win for the specified player, increments the round counter,
     * and checks whether the series has ended.
     *
     * @param playerWon {@code true} if player 1 won the round, {@code false} if player 2 won the round
     * @return {@code true} if a player has now reached {@link #WINS_REQUIRED}
     */
    public boolean recordWin(boolean playerWon) {
        if (playerWon) {
            playerOneWins++;
        } else {
            playerTwoWins++;
        }

        roundWinner = playerWon ? playerOne.getName() : playerTwo.getName();
        roundNumber++;

        if (playerOneWins >= WINS_REQUIRED || playerTwoWins >= WINS_REQUIRED) {
            seriesWinner = roundWinner;
            return true;
        }

        return false;
    }

    /**
     * Resets the round state including the character and winner
     * information.
     */
    public void resetRound() {
        roundWinner = null;
        seriesWinner = null;

        if (playerOne != null) {
            entityPool.get(playerOne).resetCharacter();
        }

        if (playerTwo != null) {
            entityPool.get(playerTwo).resetCharacter();
        }
    }

    /**
     * Resets the battle state including the mode, characters,
     * win counts, winner information, and round number.
     */
    public void resetSeries() {
        gameMode = null;

        playerOne = null;
        playerTwo = null;
        playerOneWins = 0;
        playerTwoWins = 0;

        roundWinner = null;
        seriesWinner = null;

        roundNumber = 1;
    }

    public Entity getEntity(GameCharacter type) {
        return entityPool.get(type);
    }

    public Entity getEntityOne() {
        return entityPool.get(playerOne);
    }

    public Entity getEntityTwo() {
        return entityPool.get(playerTwo);
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameCharacter getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(GameCharacter playerOne) {
        this.playerOne = playerOne;
    }

    public GameCharacter getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(GameCharacter playerTwo) {
        this.playerTwo = playerTwo;
    }

    public int getPlayerOneWins() {
        return playerOneWins;
    }

    public int getPlayerTwoWins() {
        return playerTwoWins;
    }

    public String getRoundWinner() {
        return roundWinner;
    }

    public String getSeriesWinner() {
        return seriesWinner;
    }

    public int getRoundNumber() {
        return roundNumber;
    }
}
