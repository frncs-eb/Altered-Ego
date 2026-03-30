package screens;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

public abstract class ScreenBase extends JPanel {
    protected Screen screen;
    protected List<JLabel> labels = new ArrayList<>();
    protected List<JButton> buttons = new ArrayList<>();

    public ScreenBase(Screen screen) {
        this.screen = screen;
        setLayout(null);
        initializeUI();
    }

    protected abstract void initializeUI();

    protected JLabel createLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(Color.BLACK);
        add(label);

        labels.add(label);
        return label;
    }

    protected JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setForeground(Color.BLACK);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        add(button);

        buttons.add(button);
        return button;
    }

    protected void hideButtons() {
        for (JButton button : buttons) {
            button.setVisible(false);
        }
    }

    protected void showButtons() {
        for (JButton button : buttons) {
            button.setVisible(true);
        }
    }

    protected void hideLabels() {
        for (JLabel label : labels) {
            label.setVisible(false);
        }
    }

    protected void showLabels() {
        for (JLabel label : labels) {
            label.setVisible(true);
        }
    }
}
