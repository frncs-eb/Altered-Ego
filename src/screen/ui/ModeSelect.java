package screen.ui;

import screen.*;
import util.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeSelect extends ScreenBase {
    public ModeSelect(Screen screen) {
        super(screen);
    }

    @Override
    protected void initializeUI() {
        JButton pvpButton = createButton("Player vs Player", 275, 265, 150, 50);
        pvpButton.addActionListener(e -> {
            screen.getBattle().setGameMode(GameMode.VS_PLAYER);
            screen.changeScreen(GameScreen.SELECT_CHARACTER);
        });

        JButton pveButton = createButton("Player vs Computer", 275, 315, 150, 50);
        pveButton.addActionListener(e -> {
            screen.getBattle().setGameMode(GameMode.VS_COMPUTER);
            screen.changeScreen(GameScreen.SELECT_CHARACTER);
        });

        JButton arcadeButton = createButton("Arcade", 275, 365, 150, 50);
        arcadeButton.addActionListener(e -> {
            screen.getBattle().setGameMode(GameMode.ARCADE);
            screen.changeScreen(GameScreen.SELECT_CHARACTER);
        });

        JButton backButton = createButton("Back", 275, 415, 150, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screen.changeScreen(GameScreen.TITLE);
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
