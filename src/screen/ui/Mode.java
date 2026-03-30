package screen.ui;

import screen.*;
import utils.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mode extends BaseScreen {
    public Mode(ScreenManager screenManager) {
        super(screenManager);
    }

    @Override
    protected void initializeUI() {
        JButton pvpButton = createButton("Player vs Player", 250, 250, 200, 40);
        pvpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected PVP");
            }
        });

        JButton pveButton = createButton("Player vs Computer", 250, 300, 200, 40);
        pveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected PVE");
            }
        });

        JButton arcadeButton = createButton("Arcade", 250, 350, 200, 40);
        arcadeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected Arcade");
            }
        });

        JButton backButton = createButton("Back", 250, 400, 200, 40);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected Back");
                screenManager.changeScreen(GameScreen.TITLE);
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
