package screen;

import core.*;
import screen.ui.*;
import util.*;

public class Screen {
    private final GameWindow gameWindow;
    private ScreenBase currentScreen;

    private final Title title;
    private final ModeSelect modeSelect;
    private final CharacterSelect characterSelect;
    private final Battle battle;
    private final Result result;

    private final GameBattle gameBattle;

    public Screen(GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        title = new Title(this);
        modeSelect = new ModeSelect(this);
        characterSelect = new CharacterSelect(this);
        battle = new Battle(this);
        result = new Result(this);

        gameBattle = new GameBattle();

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

        if(gameScreen == GameScreen.TITLE) {
            gameBattle.reset();
        }

        if(gameScreen == GameScreen.SELECT_CHARACTER) {
            characterSelect.resetSelection();
        }

        if(gameScreen == GameScreen.BATTLE) {
            battle.startBattle();
        }

        if(gameScreen == GameScreen.RESULT) {
            result.showResult();
        }

        gameWindow.changeWindow(currentScreen);
    }

    public GameBattle getBattle() {
        return gameBattle;
    }
}
