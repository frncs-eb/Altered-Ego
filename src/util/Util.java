package util;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * General-purpose utility methods used across the game.
 */
public class Util {
    private static final Random random = new Random();

    /**
     * Returns a random integer within {@code min} and {@code max}.
     *
     * @param min the lower bound (inclusive)
     * @param max the upper bound (inclusive)
     * @return a random integer in the range {@code min} and {@code max}
     * @throws IllegalArgumentException if {@code min} is greater than {@code max}
     */
    public static int rng(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Minimum value cannot be greater than maximum value.");
        }
        return random.nextInt(min, max + 1);
    }

    /**
     * Creates a styled {@link JLabel} with absolute bounds.
     *
     * @param text   the label text
     * @param x      x position
     * @param y      y position
     * @param width  component width
     * @param height component height
     * @return a configured {@link JLabel}
     */
    public static JLabel createLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.BLACK);
        return label;
    }

    /**
     * Creates a styled {@link JButton} with absolute bounds.
     *
     * @param text   the button text
     * @param x      x position
     * @param y      y position
     * @param width  component width
     * @param height component height
     * @return a configured {@link JButton}
     */
    public static JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.BLACK);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        return button;
    }

    /**
     * Creates a styled {@link JProgressBar} with absolute bounds.
     *
     * @param x      x position
     * @param y      y position
     * @param width  component width
     * @param height component height
     * @param max    the maximum value
     * @param color  the foreground fill color
     * @return a configured {@link JProgressBar}
     */
    public static JProgressBar createProgressBar(int x, int y, int width, int height, int max, Color color) {
        JProgressBar bar = new JProgressBar(0, max);
        bar.setBounds(x, y, width, height);
        bar.setValue(max);
        bar.setForeground(color);
        bar.setBorderPainted(false);
        return bar;
    }
}
