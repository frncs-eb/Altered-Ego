package screen.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import screen.*;
import util.*;

public class Title extends ScreenBase {
    public Title(Screen screen) {
        super(screen);
    }

    @Override
    protected void initializeUI() {
        JButton playButton = createButton("Play", 130, 452, 150, 50);
        playButton.addActionListener(e -> screen.changeScreen(GameScreen.SELECT_MODE));

        JButton exitButton = createButton("Exit", 420, 452, 150, 50);
        exitButton.addActionListener(e -> System.exit(0));
    }
}
