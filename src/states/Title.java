package states;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import utils.GameState;

public class Title extends JPanel implements State {
    private final StateManager stateManager;

    public Title(StateManager stateManager) {
        this.stateManager = stateManager;

        setLayout(null);

        createState();
    }

    @Override
    public void createState() {
        JLabel label = new JLabel("Alter Ego", SwingConstants.CENTER);
        label.setBounds(140, 120, 500, 100);
        label.setFont(new Font("Times New Roman", Font.BOLD, 70));
        label.setForeground(Color.BLACK);
        add(label);

        JButton playButton = new JButton("Play");
        playButton.setBounds(305, 350, 150, 40);
        playButton.setFont(new Font("Times New Roman", Font.BOLD, 10));
        playButton.setBackground(Color.WHITE);
        playButton.setForeground(Color.BLACK);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stateManager.changeState(GameState.GAME_MODE_SCREEN);
                System.out.println("Selected Play");
            }
        });

        add(playButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(305, 450, 150, 40);
        exitButton.setFont(new Font("Times New Roman", Font.BOLD, 10));
        exitButton.setBackground(Color.WHITE);
        exitButton.setForeground(Color.BLACK);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected Exit");
                System.exit(0);
            }
        });

        add(exitButton);
    }
}
