package screen;

import core.*;
import screen.ui.*;
import utils.*;

import javax.swing.*;

public class ScreenManager {
    private final GameWindow gameWindow;
    private BaseScreen currentScreen;

    private final BaseScreen title;
    private final BaseScreen mode;

    public ScreenManager(GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        title = new Title(this);
        mode = new Mode(this);

        currentScreen = title;
        gameWindow.changeWindow(currentScreen);
    }

    public void changeScreen(GameScreen gameScreen) {
        currentScreen = switch(gameScreen) {
            case TITLE -> title;
            case SELECT_MODE -> mode;
            case SELECT_CHARACTER -> null;
            case BATTLE -> null;
            case RESULT -> null;
        };
        gameWindow.changeWindow(currentScreen);
    }
}
