package screen;

import java.util.List;
import java.util.ArrayList;
import javax.swing.*;

public abstract class BaseScreen extends JPanel {
    protected ScreenManager screenManager;
    protected List<JButton> buttons = new ArrayList<>();

    public BaseScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
        setLayout(null);
        initializeUI();
    }

    protected abstract void initializeUI();
    protected abstract void hideButtons();
    protected abstract void showButtons();

    protected JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);

        buttons.add(button);
        add(button);
        return button;
    }
}
