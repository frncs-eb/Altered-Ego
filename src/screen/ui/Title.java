package screen.ui;

import audio.Audio;
import graphic.Graphic;
import graphic.GraphicState;
import screen.Screen;
import screen.ScreenBase;
import screen.ScreenState;
import javax.swing.*;
import java.awt.*;

public class Title extends ScreenBase {
    private static final int FRAME_WIDTH = 1024;
    private static final int FRAME_HEIGHT = 1024;
    private static final int FRAME_COUNT = 10;

    private static final int[] BG_BOUNDS = {0, 0, 706, 683};

    private Graphic bgGraphic;

    public Title(Screen screen) {
        super(screen);
    }

    @Override
    protected void initializeUI() {
        bgGraphic = new Graphic();
        loadSprites(bgGraphic);
        bgGraphic.loopAnimation(GraphicState.IDLE);
        bgGraphic.setAnimationSpeed(80);

        JButton playButton = createButton("", 205, 492, 120, 50);
        playButton.setOpaque(false);
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.setFocusable(false);
        playButton.addActionListener(e -> {
            Audio.startBGM("/soundtracks/Voltaic.wav");
            screen.changeScreen(ScreenState.SELECT_MODE);
        });

        JButton exitButton = createButton("", 380, 490, 120, 50);
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setFocusable(false);
        exitButton.addActionListener(e -> {
            Audio.stopBGM();
            System.exit(0);
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
        graphic.loadRow("/sprites/title_screen.png", FRAME_WIDTH, FRAME_HEIGHT, FRAME_COUNT);
    }
}