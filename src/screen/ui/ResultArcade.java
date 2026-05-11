package screen.ui;

import audio.Audio;
import graphic.Graphic;
import graphic.GraphicState;
import screen.Screen;
import screen.ScreenBase;
import screen.ScreenState;
import util.GameBattle;
import util.Leaderboard;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ResultArcade extends ScreenBase {
    private static final int FRAME_WIDTH = 1024;
    private static final int FRAME_HEIGHT = 1024;
    private static final int FRAME_COUNT = 1;

    private static final int[] BG_BOUNDS = {0, 0, 706, 683};

    private Graphic bgGraphic;

    private static final int MAX_RANKS = 10;
    // Leaderboard UI
    private JLabel[] rankLabels;
    // Name input for saving score
    private JTextField nameField;
    private JButton saveButton;

    private JButton mainMenuButton;

    public ResultArcade(Screen screen) {
        super(screen);
    }

    @Override
    protected void initializeUI() {
        bgGraphic = new Graphic();
        loadSprites(bgGraphic);
        bgGraphic.loopAnimation(GraphicState.IDLE);
        bgGraphic.setAnimationSpeed(80);

        // ── Result headline ───────────────────────────────────────────────────

        // ── Name entry + save ─────────────────────────────────────────────────

        nameField = new JTextField();
        nameField.setBounds(268, 248, 158, 25);
        nameField.setOpaque(false);
        nameField.setForeground(Color.WHITE);
        //nameField.setFocusable(false);
        add(nameField);

        saveButton = createButton("", 448, 248, 75, 25);
        saveButton.setOpaque(false);
        saveButton.setContentAreaFilled(false);
        //saveButton.setBorderPainted(false);
        saveButton.setFocusable(false);
        saveButton.addActionListener(e -> saveScore());

        // ── Leaderboard table ─────────────────────────────────────────────────

        rankLabels = new JLabel[MAX_RANKS];
        for (int i = 0; i < MAX_RANKS; i++) {
            rankLabels[i] = createLabel("", 155, 289 + i * 27, 400, 24);
            rankLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            rankLabels[i].setFont(new Font("Monospaced", Font.BOLD, 16));
            rankLabels[i].setForeground(Color.DARK_GRAY);
        }

        // ── Main menu button ──────────────────────────────────────────────────
        mainMenuButton = createButton("", 290, 490, 140, 45);
        mainMenuButton.setOpaque(false);
        mainMenuButton.setContentAreaFilled(false);
        mainMenuButton.setBorderPainted(false);
        mainMenuButton.setFocusable(false);
        mainMenuButton.addActionListener(e -> {
            screen.getBattle().resetSeries();
            Audio.startBGM("/soundtracks/Beauty_Flow.wav");
            screen.changeScreen(ScreenState.TITLE);
        });
    }

    @Override
    protected void onAnimationTick() {
        bgGraphic.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        bgGraphic.draw(g, BG_BOUNDS[0], BG_BOUNDS[1], BG_BOUNDS[2], BG_BOUNDS[3]);
    }

    private void loadSprites(Graphic graphic) {
        graphic.loadRow("/sprites/leaderboards_screen.png", FRAME_WIDTH, FRAME_HEIGHT, FRAME_COUNT);
    }

    /**
     * Called by Screen.changeScreen(RESULT_ARCADE) to populate the result view.
     */

    public void showResult() {
        GameBattle battle = screen.getBattle();
        boolean victory = battle.isArcadeVictory();
        int defeated = battle.getArcadeEnemiesDefeated();
        int total = battle.getTotalEnemies();


        // Reset save UI
        nameField.setText("");
        nameField.setEnabled(true);
        saveButton.setEnabled(true);

        refreshLeaderboard();
    }

    private void saveScore() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            return;
        }
        // Sanitise: strip commas so CSV stays clean
        name = name.replace(",", "");

        int kills = screen.getBattle().getArcadeEnemiesDefeated();
        Leaderboard.save(name, kills);

        nameField.setEnabled(false);
        saveButton.setEnabled(false);
        refreshLeaderboard();
    }

    private void refreshLeaderboard() {
        List<Leaderboard.Entry> entries = Leaderboard.load();

        for (JLabel label : rankLabels) {
            label.setText("");
        }

        String[] medals = {"🥇", "🥈", "🥉"};

        for (int i = 0; i < entries.size() && i < MAX_RANKS; i++) {
            Leaderboard.Entry e = entries.get(i);
            String rank = i < 3 ? medals[i] : " #" + (i + 1);
            String row = String.format("%-4s %-20s %3d kills", rank, e.name, e.kills);
            rankLabels[i].setText(row);

            // Gold / silver / bronze tint for top 3
            if (i == 0) rankLabels[i].setForeground(Color.WHITE);
            else if (i == 1) rankLabels[i].setForeground(Color.WHITE);
            else if (i == 2) rankLabels[i].setForeground(Color.WHITE);
            else rankLabels[i].setForeground(Color.WHITE);
        }
    }

    @Override
    protected void hideButtons() {
    }

    @Override
    protected void showButtons() {
    }
}