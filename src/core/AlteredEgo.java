package core;

import states.StateManager;

public class AlteredEgo {
    private final GameWindow gameWindow;
    private final StateManager stateManager;

    public AlteredEgo() {
        gameWindow = new GameWindow();
        stateManager = new StateManager(gameWindow);
    }
}
