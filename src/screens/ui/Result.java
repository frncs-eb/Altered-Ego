package screens.ui;

import screens.*;
import utils.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Result extends ScreenBase {
    public Result(Screen screen) {
        super(screen);
    }

    @Override
    protected void initializeUI() {
        JButton playButton = createButton("Rematch", 130, 452, 150, 50);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected Rematch");
                screen.changeScreen(GameScreen.BATTLE);
            }
        });

        JButton toTitleButton = createButton("Title", 420, 452, 150, 50);
        toTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected Title");
                screen.changeScreen(GameScreen.TITLE);
            }
        });
    }
}
