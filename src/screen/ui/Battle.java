package screen.ui;

import entity.Entity;
import entity.Skill;
import screen.Screen;
import screen.ScreenBase;
import util.GameBattle;
import util.GameMode;
import util.GameScreen;
import javax.swing.*;
import java.awt.*;

public class Battle extends ScreenBase {
    private static final int TURN_TIME = 10;
    private Entity player1;
    private Entity player2;
    private boolean isPlayer1Turn;
    private boolean battleOver;
    private JLabel p1NameLabel;
    private JLabel p1HpLabel;
    private JLabel p1ManaLabel;
    private JProgressBar p1HpBar;
    private JProgressBar p1ManaBar;
    private JLabel p2NameLabel;
    private JLabel p2HpLabel;
    private JLabel p2ManaLabel;
    private JProgressBar p2HpBar;
    private JProgressBar p2ManaBar;
    private JLabel roundLabel;
    private JLabel scoreLabel;
    private JLabel countdownLabel;
    private JLabel turnLabel;
    private JLabel combatLog;
    private JButton basicAttackButton;
    private JButton skill1Button;
    private JButton skill2Button;
    private JButton skill3Button;
    private Timer turnTimer;
    private int turnTimeLeft;

    public Battle(Screen screen) {
        super(screen);
    }

    @Override
    protected void initializeUI() {
        p1NameLabel = createLabel("P1", 45, 30, 200, 20);
        p1NameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        p1HpLabel = createLabel("HP: 500/500", 45, 60, 200, 20);
        p1HpLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        p1ManaLabel = createLabel("MP: 200/200", 45, 100, 200, 20);
        p1ManaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        p1HpBar = createBar(45, 82, 200, 14, Color.decode("#4caf50"));
        p1ManaBar = createBar(45, 122, 200, 14, Color.decode("#2196f3"));

        p2NameLabel = createLabel("P2", 465, 30, 200, 20);
        p2NameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        p2HpLabel = createLabel("HP: 500/500", 465, 60, 200, 20);
        p2HpLabel.setHorizontalAlignment(SwingConstants.LEFT);
        p2ManaLabel = createLabel("MP: 200/200", 465, 100, 200, 20);
        p2ManaLabel.setHorizontalAlignment(SwingConstants.LEFT);
        p2HpBar = createBar(465, 82, 200, 14, Color.decode("#4caf50"));
        p2ManaBar = createBar(465, 122, 200, 14, Color.decode("#2196f3"));

        roundLabel = createLabel("Round 1", 255, 10, 200, 25);
        roundLabel.setHorizontalAlignment(SwingConstants.CENTER);
        roundLabel.setFont(new Font("Arial", Font.BOLD, 16));
        roundLabel.setForeground(Color.DARK_GRAY);

        scoreLabel = createLabel("0 - 0", 255, 30, 200, 25);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        scoreLabel.setForeground(Color.DARK_GRAY);

        countdownLabel = createLabel("", 330, 50, 50, 25);
        countdownLabel.setHorizontalAlignment(SwingConstants.CENTER);
        countdownLabel.setFont(new Font("Arial", Font.BOLD, 16));
        countdownLabel.setForeground(Color.RED);

        turnLabel = createLabel("", 155, 160, 400, 30);
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        turnLabel.setForeground(Color.DARK_GRAY);

        combatLog = createLabel("", 55, 210, 600, 30);
        combatLog.setHorizontalAlignment(SwingConstants.CENTER);
        combatLog.setFont(new Font("Arial", Font.ITALIC, 14));
        combatLog.setForeground(Color.DARK_GRAY);

        basicAttackButton = createButton("Basic Attack", 255, 550, 200, 50);
        skill1Button = createButton("", 65, 620, 140, 50);
        skill2Button = createButton("", 285, 620, 140, 50);
        skill3Button = createButton("", 500, 620, 140, 50);

        basicAttackButton.addActionListener(e -> handleAction(0));
        skill1Button.addActionListener(e -> handleAction(1));
        skill2Button.addActionListener(e -> handleAction(2));
        skill3Button.addActionListener(e -> handleAction(3));
    }

    public void startBattle() {
        GameBattle battle = screen.getBattle();
        battle.resetRound();

        player1 = battle.getEntityOne();
        player2 = battle.getEntityTwo();

        isPlayer1Turn = true;
        battleOver = false;

        refreshNamesLabels();
        refreshBars();
        refreshRoundLabels();
        refreshTurnLabel();
        refreshSkillButtons(player1);
        combatLog.setText("Round " + battle.getRoundNumber() + " — Fight!");

        boolean isCpu = battle.getGameMode() != GameMode.VS_PLAYER;
        if (isCpu) {
            startTurnTimer();
        }
    }

    private void handleAction(int actionIndex) {
        if (battleOver) {
            return;
        }

        Entity attacker = isPlayer1Turn ? player1 : player2;
        Entity defender = isPlayer1Turn ? player2 : player1;

        int damage = 0;
        String logMessage;

        if (actionIndex == 0) {
            int hpBefore = defender.getCurrentHP();
            attacker.basicAttack(defender);
            damage = hpBefore - defender.getCurrentHP();
            logMessage = attacker.getName() + " used Basic Attack for " + damage + " damage!";
        } else {
            Skill skill = attacker.getSkill(actionIndex);
            if (skill.isCooldown()) {
                combatLog.setText(skill.getName() + " is on cooldown! (" + skill.getCurrentCooldown() + " turns left)");
                return;
            }
            if (attacker.getCurrentMana() < skill.getManaCost()) {
                combatLog.setText("Not enough mana to use " + skill.getName() + "!");
                return;
            }
            int hpBefore = defender.getCurrentHP();
            attacker.useSkill(actionIndex, defender);
            damage = hpBefore - defender.getCurrentHP();
            logMessage = attacker.getName() + " used " + skill.getName() + " for " + damage + " damage!";
        }

        attacker.reduceCooldowns();
        attacker.regenMana();
        combatLog.setText(logMessage);
        refreshBars();

        if (!defender.isAlive()) {
            endBattle(attacker);
            return;
        }

        isPlayer1Turn = !isPlayer1Turn;
        refreshTurnLabel();

        boolean isCpu = screen.getBattle().getGameMode() != GameMode.VS_PLAYER;
        if (isCpu && !isPlayer1Turn) {
            scheduleCpuTurn();
        } else {
            refreshSkillButtons(isPlayer1Turn ? player1 : player2);
            if (isCpu) {
                startTurnTimer();
            }
        }
    }

    private void startTurnTimer() {
        stopTurnTimer();
        turnTimeLeft = TURN_TIME;
        countdownLabel.setVisible(true);
        countdownLabel.setText(turnTimeLeft + "s");

        turnTimer = new Timer(1000, e -> {
            turnTimeLeft--;
            countdownLabel.setText(turnTimeLeft + "s");
            if (turnTimeLeft <= 0) {
                stopTurnTimer();
                combatLog.setText("Time's up! Auto basic attack.");
                handleAction(0);
            }
        });
        turnTimer.start();
    }

    private void stopTurnTimer() {
        if (turnTimer != null && turnTimer.isRunning()) {
            turnTimer.stop();
        }
        countdownLabel.setVisible(false);
    }

    private void scheduleCpuTurn() {
        setActionButtonsEnabled(false);
        Timer timer = new Timer(1000, e -> {
            cpuTakeTurn();
            ((Timer) e.getSource()).stop();
        });
        timer.start();
    }

    private void cpuTakeTurn() {
        if (battleOver) {
            return;
        }

        int[] priority = {3, 2, 1};
        for (int i : priority) {
            Skill skill = player2.getSkill(i);
            if (!skill.isCooldown() && player2.getCurrentMana() >= skill.getManaCost()) {
                handleAction(i);
                return;
            }
        }
        handleAction(0);
    }

    private void endBattle(Entity winner) {
        battleOver = true;
        setActionButtonsEnabled(false);

        GameBattle battle = screen.getBattle();
        boolean p1Won = winner == player1;
        boolean seriesOver = battle.recordWin(p1Won);

        refreshRoundLabels();

        if (seriesOver) {
            combatLog.setText(winner.getName() + " wins the series!");
            Timer timer = new Timer(2000, e -> {
                screen.changeScreen(GameScreen.RESULT);
                ((Timer) e.getSource()).stop();
            });
            timer.start();
        } else {
            combatLog.setText(winner.getName() + " wins round " + (battle.getRoundNumber() - 1) + "!");
            Timer timer = new Timer(2000, e -> {
                screen.changeScreen(GameScreen.BATTLE);
                ((Timer) e.getSource()).stop();
            });
            timer.start();
        }
    }

    private void refreshNamesLabels() {
        p1NameLabel.setText(player1.getName());
        p2NameLabel.setText(player2.getName());
    }

    private void refreshBars() {
        p1HpLabel.setText("HP: " + player1.getCurrentHP() + "/" + player1.getBaseHP());
        p1HpBar.setMaximum(player1.getBaseHP());
        p1HpBar.setValue(player1.getCurrentHP());

        p1ManaLabel.setText("MP: " + player1.getCurrentMana() + "/" + player1.getBaseMana());
        p1ManaBar.setMaximum(player1.getBaseMana());
        p1ManaBar.setValue(player1.getCurrentMana());

        p2HpLabel.setText("HP: " + player2.getCurrentHP() + "/" + player2.getBaseHP());
        p2HpBar.setMaximum(player2.getBaseHP());
        p2HpBar.setValue(player2.getCurrentHP());

        p2ManaLabel.setText("MP: " + player2.getCurrentMana() + "/" + player2.getBaseMana());
        p2ManaBar.setMaximum(player2.getBaseMana());
        p2ManaBar.setValue(player2.getCurrentMana());
    }

    private void refreshRoundLabels() {
        GameBattle state = screen.getBattle();
        roundLabel.setText("Round " + state.getRoundNumber());
        scoreLabel.setText(state.getPlayerOneWins() + " - " + state.getPlayerTwoWins());
    }

    private void refreshTurnLabel() {
        Entity active = isPlayer1Turn ? player1 : player2;
        boolean isCpu = screen.getBattle().getGameMode() != GameMode.VS_PLAYER && !isPlayer1Turn;
        turnLabel.setText(active.getName() + "'s turn" + (isCpu ? " (CPU)" : ""));
    }

    private void refreshSkillButtons(Entity active) {
        setActionButtonsEnabled(true);
        for (int i = 1; i <= 3; i++) {
            Skill skill = active.getSkill(i);
            JButton btn = i == 1 ? skill1Button : i == 2 ? skill2Button : skill3Button;
            boolean usable = !skill.isCooldown() && active.getCurrentMana() >= skill.getManaCost();
            if (skill.isCooldown()) {
                btn.setText(skill.getName() + " (CD:" + skill.getCurrentCooldown() + ")");
            } else {
                btn.setText(skill.getName() + " (" + skill.getManaCost() + "MP)");
            }
            btn.setEnabled(usable);
        }
    }

    private void setActionButtonsEnabled(boolean enabled) {
        basicAttackButton.setEnabled(enabled);
        skill1Button.setEnabled(enabled);
        skill2Button.setEnabled(enabled);
        skill3Button.setEnabled(enabled);
    }

    @Override
    protected void hideButtons() {

    }

    @Override
    protected void showButtons() {

    }
}
