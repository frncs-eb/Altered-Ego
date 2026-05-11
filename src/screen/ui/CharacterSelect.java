package screen.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

import audio.Audio;
import entity.EntityState;
import graphic.Graphic;
import graphic.GraphicState;
import screen.*;
import util.*;

public class CharacterSelect extends ScreenBase {
    private static final int FRAME_WIDTH = 1024;
    private static final int FRAME_HEIGHT = 1024;
    private static final int FRAME_COUNT = 11;

    private static final int[] BG_BOUNDS = {0, 0, 706, 683};

    private Graphic bgGraphic;

    private Map<EntityState, JButton> characterButtons;
    private int selectionRound = 1;

    public CharacterSelect(Screen screen) {
        super(screen);
    }

    @Override
    protected void initializeUI() {

        bgGraphic = new Graphic();
        loadSprites(bgGraphic);
        bgGraphic.loopAnimation(GraphicState.IDLE);
        bgGraphic.setAnimationSpeed(80);

        characterButtons = new LinkedHashMap<>();

        int col1 = 125, col2 = 405;
        int yStart = 140;
        int gap = 83;

        EntityState[] all = EntityState.values();
        for (int i = 0; i < all.length; i++) {
            EntityState gc = all[i];
            int x = (i % 2 == 0) ? col1 : col2;
            int y = yStart + ((i / 2) * gap);
            JButton btn = createButton("", x, y, 180, 70);
            btn.setOpaque(false);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setFocusable(false);
            btn.addActionListener(e -> onSelection(gc));
            characterButtons.put(gc, btn);
        }

        JButton backButton = createButton("Back", 255, 585, 200, 50);
        backButton.addActionListener(e -> {
            Audio.startBGM("/soundtracks/Voltaic.wav");
            screen.changeScreen(ScreenState.SELECT_MODE);
        });
    }

    @Override
    protected void onAnimationTick() {
        bgGraphic.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        bgGraphic.draw(g, BG_BOUNDS[0], BG_BOUNDS[1], BG_BOUNDS[2], BG_BOUNDS[3]);
    }

    private void loadSprites(Graphic graphic) {
        graphic.loadRow("/sprites/characterselect_screen.png", FRAME_WIDTH, FRAME_HEIGHT, FRAME_COUNT);
    }

    private void onSelection(EntityState character) {
        GameBattle battle = screen.getBattle();
        ModeState mode = battle.getGameMode();
        boolean isPvP = mode == ModeState.VS_PLAYER;
        boolean isArcade = mode == ModeState.ARCADE;

        if (selectionRound == 1) {
            battle.setPlayerOne(character);
            characterButtons.get(character).setEnabled(false);

            if (isPvP) {
                selectionRound = 2;
            } else if (isArcade) {
                List<EntityState> enemies = new ArrayList<>();
                for (EntityState gc : EntityState.values()) {
                    if (gc != character) enemies.add(gc);
                }
                java.util.Collections.shuffle(enemies);
                battle.resetArcade(enemies);
                Audio.startBGM("/soundtracks/Harmful_or_Fatal.wav");
                screen.changeScreen(ScreenState.BATTLE_ARCADE);
            } else {
                List<EntityState> available = new ArrayList<>();
                for (Map.Entry<EntityState, JButton> entry : characterButtons.entrySet()) {
                    if (entry.getValue().isEnabled()) available.add(entry.getKey());
                }
                EntityState cpuPick = available.get(Util.rng(0, available.size() - 1));
                battle.setPlayerTwo(cpuPick);
                Audio.startBGM("/soundtracks/Harmful_or_Fatal.wav");
                screen.changeScreen(ScreenState.BATTLE);
            }
        } else {
            // PvP round 2 selection
            battle.setPlayerTwo(character);
            Audio.startBGM("/soundtracks/Harmful_or_Fatal.wav");
            screen.changeScreen(ScreenState.BATTLE);
        }
    }

    public void resetSelection() {
        selectionRound = 1;
        for (JButton btn : characterButtons.values()) {
            btn.setEnabled(true);
        }
    }

    @Override
    protected void hideButtons() { }

    @Override
    protected void showButtons() { }
}