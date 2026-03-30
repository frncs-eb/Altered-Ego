package screens.ui;

import screens.*;
import utils.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CharacterSelect extends ScreenBase {
    public CharacterSelect(Screen screen) {
        super(screen);
    }

    @Override
    protected void initializeUI() {
        JButton char1Button = createButton("Cosmic Dasel", 275, 215, 150, 50);
        char1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected Cosmic Dasel");
                screen.changeScreen(GameScreen.BATTLE);
            }
        });

        JButton char2Button = createButton("Khylle The Reaper", 275, 265, 150, 50);
        char2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected Khylle The Reaper");
                screen.changeScreen(GameScreen.BATTLE);
            }
        });

        JButton char3Button = createButton("Earl", 275, 315, 150, 50);
        char3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected Earl");
                screen.changeScreen(GameScreen.BATTLE);
            }
        });

        JButton char4Button = createButton("The One John", 275, 365, 150, 50);
        char4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected The One John");
                screen.changeScreen(GameScreen.BATTLE);
            }
        });

        JButton char5Button = createButton("And Rew", 275, 415, 150, 50);
        char5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected And Rew");
                screen.changeScreen(GameScreen.BATTLE);
            }
        });

        JButton backButton = createButton("Back", 275, 465, 150, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected Back");
                screen.changeScreen(GameScreen.SELECT_MODE);
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
