package core;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        setTitle("Altered Ego");
        setSize(720, 720);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void changePanel(JPanel newPanel) {
        getContentPane().removeAll();
        getContentPane().add(newPanel);
        revalidate();
        repaint();
    }
}