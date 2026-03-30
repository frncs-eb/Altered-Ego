package screen.ui;

import screen.*;
import utils.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Title extends BaseScreen {
    public Title(ScreenManager screenManager) {
        super(screenManager);
    }

    @Override
    protected void initializeUI() {
        JButton playButton = createButton("Play", 150, 452, 150, 50);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected Play");
                screenManager.changeScreen(GameScreen.SELECT_MODE);
            }
        });

        JButton exitButton = createButton("Exit", 425, 452, 150, 50);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected Exit");
                System.exit(0);
            }
        });
    }

    @Override
    protected void hideButtons() {

    }

    @Override
    protected void showButtons() {

    }
}
