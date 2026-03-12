package states;

import core.GameWindow;
import utils.GameState;

import javax.swing.*;

public class StateManager {
    private final GameWindow gameWindow;
    private State currentState;

    private final Title title;
    private final Mode mode;

    public StateManager(GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        title = new Title(this);
        mode = new Mode(this);

        currentState = title;
        gameWindow.changeWindow((JPanel)currentState);
    }

    public void changeState(GameState gameState) {
        currentState = switch(gameState) {
            case TITTLE_SCREEN -> title;
            case GAME_MODE_SCREEN -> mode;
        };
        gameWindow.changeWindow((JPanel)currentState);
    }
}
