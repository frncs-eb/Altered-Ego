package core;

import screen.Screen;
import javax.swing.*;

public class AlteredEgo {
    private GameFrame gameFrame;
    private Screen screen;

    public AlteredEgo() {
        SwingUtilities.invokeLater(() -> {
            gameFrame = new GameFrame();
            screen = new Screen(gameFrame);
        });
    }
}