package screen.ui;

import java.awt.*;
import javax.swing.*;
import screen.*;
import util.*;

public class Result extends ScreenBase {
    private JLabel winnerLabel;
    private JLabel scoreLabel;

    public Result(Screen screen) {
        super(screen);
    }

    @Override
    protected void initializeUI() {
        winnerLabel = createLabel("", 155, 250, 400, 50);
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 26));
        winnerLabel.setForeground(Color.BLACK);

        scoreLabel = createLabel("", 155, 310, 400, 50);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        scoreLabel.setForeground(Color.DARK_GRAY);

        JButton rematchButton = createButton("Rematch", 105, 452, 200, 50);
        rematchButton.addActionListener(e -> {
            GameBattle battle = screen.getBattle();
            GameMode mode = battle.getGameMode();
            GameCharacter p1 = battle.getPlayer1();
            GameCharacter p2 = battle.getPlayer2();
            battle.reset();
            battle.setGameMode(mode);
            battle.setPlayer1(p1);
            battle.setPlayer2(p2);
            screen.changeScreen(GameScreen.BATTLE);
        });

        JButton toTitleButton = createButton("Title", 410, 452, 200, 50);
        toTitleButton.addActionListener(e -> screen.changeScreen(GameScreen.TITLE));
    }

    public void showResult() {
        GameBattle state = screen.getBattle();
        String winner = state.getSeriesWinner();
        winnerLabel.setText(winner != null ? winner + " wins!" : "");
        scoreLabel.setText(state.getP1Wins() + " - " + state.getP2Wins());
    }
}
