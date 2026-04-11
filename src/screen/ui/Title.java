package screen.ui;

import javax.swing.*;
import screen.*;
import util.*;

public class Title extends ScreenBase {
    public Title(Screen screen) {
        super(screen);
    }

    @Override
    protected void initializeUI() {
        JButton playButton = createButton("Play", 105, 452, 200, 50);
        playButton.addActionListener(e -> screen.changeScreen(GameScreen.SELECT_MODE));

        JButton exitButton = createButton("Exit", 410, 452, 200, 50);
        exitButton.addActionListener(e -> System.exit(0));
    }
}
