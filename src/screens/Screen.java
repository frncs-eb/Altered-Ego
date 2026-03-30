package screens;

import core.*;
import screens.ui.*;
import utils.*;

public class Screen {
    private final GameWindow gameWindow;
    private ScreenBase currentScreen;

    private final ScreenBase title;
    private final ScreenBase modeSelect;
    private final ScreenBase characterSelect;
    private final ScreenBase battle;
    private final ScreenBase result;

    public Screen(GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        title = new Title(this);
        modeSelect = new ModeSelect(this);
        characterSelect = new CharacterSelect(this);
        battle = new Battle(this);
        result = new Result(this);

        currentScreen = title;
        gameWindow.changeWindow(currentScreen);
    }

    public void changeScreen(GameScreen gameScreen) {
        currentScreen = switch(gameScreen) {
            case TITLE -> title;
            case SELECT_MODE -> modeSelect;
            case SELECT_CHARACTER -> characterSelect;
            case BATTLE -> battle;
            case RESULT -> result;
        };
        gameWindow.changeWindow(currentScreen);
    }
}
