package screens.ui;

import screens.*;
import utils.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Title extends ScreenBase {
    public Title(Screen screen) {
        super(screen);
    }

    @Override
    protected void initializeUI() {
        JButton playButton = createButton("Play", 130, 452, 150, 50);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected Play");
                screen.changeScreen(GameScreen.SELECT_MODE);
            }
        });

        JButton exitButton = createButton("Exit", 420, 452, 150, 50);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected Exit");
                System.exit(0);
            }
        });
    }
}
