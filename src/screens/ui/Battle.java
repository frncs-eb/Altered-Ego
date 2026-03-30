package screens.ui;

import screens.*;
import utils.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Battle extends ScreenBase {
    public Battle(Screen screen) {
        super(screen);
    }

    @Override
    protected void initializeUI() {
        JButton toTitleButton = createButton("Result", 275, 452, 150, 50);
        toTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected Result");
                screen.changeScreen(GameScreen.RESULT);
            }
        });
    }
}
