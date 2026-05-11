package screen;

import audio.Audio;
import core.*;
import screen.ui.*;
import util.*;

public class Screen {
    private final GameFrame gameFrame;
    private ScreenBase currentScreen;

    private final Title title;
    private final ModeSelect modeSelect;
    private final CharacterSelect characterSelect;
    private final Battle battle;
    private final BattleArcade battleArcade;
    private final Result result;
    private final ResultArcade resultArcade;

    private final GameBattle gameBattle;

    public Screen(GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        title = new Title(this);
        modeSelect = new ModeSelect(this);
        characterSelect = new CharacterSelect(this);
        battle = new Battle(this);
        battleArcade = new BattleArcade(this);
        result = new Result(this);
        resultArcade = new ResultArcade(this);

        gameBattle = new GameBattle();

        currentScreen = title;
        gameFrame.changePanel(currentScreen);
        gameFrame.setVisible(true);

        Audio.startBGM("/soundtracks/Beauty_Flow.wav");
    }

    public void changeScreen(ScreenState gameScreen) {
        currentScreen = switch(gameScreen) {
            case TITLE -> title;
            case SELECT_MODE -> modeSelect;
            case SELECT_CHARACTER -> characterSelect;
            case BATTLE -> battle;
            case BATTLE_ARCADE -> battleArcade;
            case RESULT -> result;
            case RESULT_ARCADE -> resultArcade;
        };

        if(gameScreen == ScreenState.TITLE) {
            gameBattle.resetSeries();
        }

        if(gameScreen == ScreenState.BATTLE) {
            battle.startBattle();
        }

        if(gameScreen == ScreenState.BATTLE_ARCADE) {
            battleArcade.startBattle();
        }

        if(gameScreen == ScreenState.RESULT) {
            result.showResult();
        }

        if(gameScreen == ScreenState.RESULT_ARCADE) {
            resultArcade.showResult();
        }

        gameFrame.changePanel(currentScreen);
    }

    public CharacterSelect getCharacterSelect() {
        return characterSelect;
    }

    public GameBattle getBattle() {
        return gameBattle;
    }
}