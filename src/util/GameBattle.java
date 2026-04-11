package util;

import entity.Entity;

public class GameBattle {
    private final EntityPool entityPool = new EntityPool();

    private GameMode gameMode;

    private GameCharacter player1;
    private GameCharacter player2;

    private String roundWinner;
    private String seriesWinner;

    private int p1Wins = 0;
    private int p2Wins = 0;

    private int roundNumber = 1;

    private static final int WINS_REQUIRED = 2;

    public void reset() {
        gameMode = null;

        player1 = null;
        player2 = null;

        roundWinner = null;
        seriesWinner = null;

        p1Wins = 0;
        p2Wins = 0;

        roundNumber = 1;
    }

    public void resetRound() {
        roundWinner = null;
        seriesWinner = null;

        if(player1 != null) {
            entityPool.get(player1).resetCharacter();
        }
        if(player2 != null) {
            entityPool.get(player2).resetCharacter();
        }
    }

    public boolean recordWin(boolean player1Won) {
        if(player1Won) {
            p1Wins++;
        } else {
            p2Wins++;
        }

        roundWinner = player1Won ? player1.getName() : player2.getName();
        roundNumber++;

        if(p1Wins >= WINS_REQUIRED || p2Wins >= WINS_REQUIRED) {
            seriesWinner = roundWinner;
            return true;
        }
        return false;
    }

    public Entity getEntity(GameCharacter type) {
        return entityPool.get(type);
    }

    public Entity getEntity1() {
        return entityPool.get(player1);
    }

    public Entity getEntity2() {
        return entityPool.get(player2);
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public GameCharacter getPlayer1() {
        return player1;
    }

    public GameCharacter getPlayer2() {
        return player2;
    }

    public String getRoundWinner() {
        return roundWinner;
    }

    public String getSeriesWinner() {
        return seriesWinner;
    }

    public int getP1Wins() {
        return p1Wins;
    }

    public int getP2Wins() {
        return p2Wins;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void setPlayer1(GameCharacter player1) {
        this.player1 = player1;
    }

    public void setPlayer2(GameCharacter player2) {
        this.player2 = player2;
    }
}
