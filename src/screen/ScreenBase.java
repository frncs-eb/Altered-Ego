package screen;

import util.Util;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ScreenBase extends JPanel {
    private static final int ANIMATION_TICK_MS = 16;
    protected final Screen screen;
    protected final List<JLabel> labels = new ArrayList<>();
    protected final List<JButton> buttons = new ArrayList<>();
    protected final List<JProgressBar> progressBars = new ArrayList<>();
    private final Timer animationTimer;

    protected ScreenBase(Screen screen) {
        this.screen = screen;
        setLayout(null);

        animationTimer = new Timer(ANIMATION_TICK_MS, e -> {
            onAnimationTick();
            repaint();
        });

        initializeUI();
    }

    protected abstract void initializeUI();

    protected abstract void onAnimationTick();

    @Override
    public void addNotify() {
        super.addNotify();
        animationTimer.start();
    }

    @Override
    public void removeNotify() {
        animationTimer.stop();
        super.removeNotify();
    }

    protected JLabel createLabel(String text, int x, int y, int width, int height) {
        JLabel label = Util.createLabel(text, x, y, width, height);
        add(label);
        labels.add(label);
        return label;
    }

    protected JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = Util.createButton(text, x, y, width, height);
        add(button);
        buttons.add(button);
        return button;
    }

    protected JProgressBar createBar(int x, int y, int width, int height, Color color) {
        JProgressBar bar = Util.createProgressBar(500, x, y, width, height, color);
        add(bar);
        progressBars.add(bar);
        return bar;
    }

    protected void showLabels() {
        labels.forEach(l -> l.setVisible(true));
    }

    protected void hideLabels() {
        labels.forEach(l -> l.setVisible(false));
    }

    protected void showButtons() {
        buttons.forEach(b -> b.setVisible(true));
    }

    protected void hideButtons() {
        buttons.forEach(b -> b.setVisible(false));
    }
}