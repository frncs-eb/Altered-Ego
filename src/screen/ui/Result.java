package screen.ui;

import audio.Audio;
import graphic.Graphic;
import graphic.GraphicState;
import screen.Screen;
import screen.ScreenBase;
import util.GameBattle;
import entity.EntityState;
import screen.ModeState;
import screen.ScreenState;
import javax.swing.*;
import java.awt.*;

public class Result extends ScreenBase {
    private static final int FRAME_WIDTH = 1024;
    private static final int FRAME_HEIGHT = 1024;
    private static final int FRAME_COUNT = 1;

    private static final int[] BG_BOUNDS = {0, 0, 706, 683};

    private Graphic bgGraphic;

    private JLabel winnerLabel;
    private JLabel scoreLabel;

    public Result(Screen screen) {
        super(screen);
    }

    @Override
    protected void initializeUI() {
        bgGraphic = new Graphic();
        loadSprites(bgGraphic);
        bgGraphic.loopAnimation(GraphicState.IDLE);
        bgGraphic.setAnimationSpeed(80);

        winnerLabel = createLabel("", 155, 270, 400, 50);
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 26));
        winnerLabel.setForeground(Color.WHITE);

        scoreLabel = createLabel("", 155, 340, 400, 50);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setForeground(Color.WHITE);

        JButton rematchButton = createButton("", 155, 458, 200, 70);
        rematchButton.setOpaque(false);
        rematchButton.setContentAreaFilled(false);
        rematchButton.setBorderPainted(false);
        rematchButton.setFocusable(false);
        rematchButton.addActionListener(e -> {
            GameBattle battle = screen.getBattle();
            ModeState mode = battle.getGameMode();
            EntityState p1 = battle.getPlayerOne();
            EntityState p2 = battle.getPlayerTwo();
            battle.resetSeries();
            battle.setGameMode(mode);
            battle.setPlayerOne(p1);
            battle.setPlayerTwo(p2);
            screen.changeScreen(ScreenState.BATTLE);
        });

        JButton toTitleButton = createButton("", 360, 458, 190, 70);
        toTitleButton.setOpaque(false);
        toTitleButton.setContentAreaFilled(false);
        toTitleButton.setBorderPainted(false);
        toTitleButton.setFocusable(false);
        toTitleButton.addActionListener(e -> {
            Audio.startBGM("/soundtracks/Beauty_Flow.wav");
            screen.changeScreen(ScreenState.TITLE);
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
        graphic.loadRow("/sprites/result_screen.png", FRAME_WIDTH, FRAME_HEIGHT, FRAME_COUNT);
    }

    public void showResult() {
        GameBattle state = screen.getBattle();
        String winner = state.getSeriesWinner();
        winnerLabel.setText(winner != null ? winner + " wins!" : "");
        scoreLabel.setText(state.getPlayerOneWins() + " - " + state.getPlayerTwoWins());
    }
}
