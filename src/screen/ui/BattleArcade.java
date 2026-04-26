package screen.ui;

import java.awt.*;
import javax.swing.*;
import entity.*;
import screen.*;
import util.*;

public class BattleArcade extends ScreenBase {
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
    private JLabel arcadeProgressLabel;
    private JLabel countdownLabel;
    private JLabel turnLabel;
    private JLabel combatLog;

    private JButton basicAttackButton;
    private JButton skill1Button;
    private JButton skill2Button;
    private JButton skill3Button;

    private static final int TURN_TIME = 10;
    private Timer turnTimer;
    private int turnTimeLeft;

    public BattleArcade(Screen screen) {
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
        p1HpBar  = createBar(45, 82,  200, 14, Color.decode("#4caf50"));
        p1ManaBar = createBar(45, 122, 200, 14, Color.decode("#2196f3"));

        p2NameLabel = createLabel("Enemy", 465, 30, 200, 20);
        p2NameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        p2HpLabel = createLabel("HP: 500/500", 465, 60, 200, 20);
        p2HpLabel.setHorizontalAlignment(SwingConstants.LEFT);
        p2ManaLabel = createLabel("MP: 200/200", 465, 100, 200, 20);
        p2ManaLabel.setHorizontalAlignment(SwingConstants.LEFT);
        p2HpBar  = createBar(465, 82,  200, 14, Color.decode("#4caf50"));
        p2ManaBar = createBar(465, 122, 200, 14, Color.decode("#2196f3"));

        roundLabel = createLabel("Fight 1", 255, 10, 200, 25);
        roundLabel.setHorizontalAlignment(SwingConstants.CENTER);
        roundLabel.setFont(new Font("Arial", Font.BOLD, 16));
        roundLabel.setForeground(Color.DARK_GRAY);

        arcadeProgressLabel = createLabel("", 255, 35, 200, 20);
        arcadeProgressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        arcadeProgressLabel.setFont(new Font("Arial", Font.BOLD, 13));
        arcadeProgressLabel.setForeground(new Color(0x8B4513));

        countdownLabel = createLabel("", 330, 58, 50, 25);
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
        skill1Button      = createButton("", 65,  620, 140, 50);
        skill2Button      = createButton("", 285, 620, 140, 50);
        skill3Button      = createButton("", 500, 620, 140, 50);

        basicAttackButton.addActionListener(e -> handleAction(0));
        skill1Button     .addActionListener(e -> handleAction(1));
        skill2Button     .addActionListener(e -> handleAction(2));
        skill3Button     .addActionListener(e -> handleAction(3));
    }

    /**
     * Starts or resumes an arcade fight.
     * GameBattle.setupArcade() must have been called once before the first
     * fight. After each victory, advanceArcadeEnemy() updates player2 so
     * this method simply reads the current state.
     * Player HP/mana intentionally carry over between fights.
     */

    public void startBattle() {
        GameBattle battle = screen.getBattle();

        battle.resetRound();           // reset enemy HP, not player

        player1 = battle.getEntityOne();
        player2 = battle.getEntityTwo();

        if (player1 == null || player2 == null) {
            combatLog.setText("Error: battle not initialised.");
            return;
        }

        isPlayer1Turn = true;
        battleOver    = false;

        refreshHUD();
        combatLog.setText("Enemy " + (battle.getTotalEnemiesDefeated() + 1) + " — Fight!");

        startTurnTimer();
    }

    private void handleAction(int actionIndex) {
        if (battleOver) return;

        Entity attacker = isPlayer1Turn ? player1 : player2;
        Entity defender = isPlayer1Turn ? player2 : player1;

        String logMessage;

        if (actionIndex == 0) {
            int hpBefore = defender.getCurrentHP();
            attacker.basicAttack(defender);
            int damage = hpBefore - defender.getCurrentHP();
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
            int damage = hpBefore - defender.getCurrentHP();
            logMessage = attacker.getName() + " used " + skill.getName() + " for " + damage + " damage!";
        }

        attacker.reduceCooldowns();
        attacker.regenMana();
        combatLog.setText(logMessage);
        refreshBars();

        if (!defender.isAlive()) {
            handleKnockout(attacker == player1);
            return;
        }

        isPlayer1Turn = !isPlayer1Turn;
        refreshTurnLabel();

        if (!isPlayer1Turn) {
            scheduleCpuTurn();
        } else {
            refreshSkillButtons(player1);
            startTurnTimer();
        }
    }

    /**
     * @param playerWon true = player knocked out the enemy; false = enemy knocked out the player.
     */

    private void handleKnockout(boolean playerWon) {
        battleOver = true;
        stopTurnTimer();
        setActionButtonsEnabled(false);

        GameBattle battle = screen.getBattle();

        if (playerWon) {
            boolean allCleared = battle.advanceArcadeEnemy();
            int defeated = battle.getTotalEnemiesDefeated();
            combatLog.setText(player2.getName() + " defeated! Enemies cleared: " + defeated);

            if (allCleared) {
                Timer t = new Timer(2000, e -> {
                    screen.changeScreen(GameScreen.ARCADE_RESULT);
                    ((Timer) e.getSource()).stop();
                });
                t.start();
            } else {
                Timer t = new Timer(2000, e -> {
                    screen.changeScreen(GameScreen.BATTLE_ARCADE);
                    ((Timer) e.getSource()).stop();
                });
                t.start();
            }
        } else {
            combatLog.setText("Defeated by " + player2.getName() + "! Game Over.");
            Timer t = new Timer(2000, e -> {
                screen.changeScreen(GameScreen.ARCADE_RESULT);
                ((Timer) e.getSource()).stop();
            });
            t.start();
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
        Timer t = new Timer(1000, e -> {
            cpuTakeTurn();
            ((Timer) e.getSource()).stop();
        });
        t.start();
    }

    private void cpuTakeTurn() {
        if (battleOver) return;
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

    private void refreshHUD() {
        GameBattle battle = screen.getBattle();

        p1NameLabel.setText(player1.getName());
        p2NameLabel.setText(player2.getName());

        int fightNumber = battle.getTotalEnemiesDefeated() + 1;
        int total       = battle.getTotalEnemies();
        roundLabel.setText("Fight " + fightNumber);
        arcadeProgressLabel.setText("Enemy " + fightNumber + " / " + total);

        refreshBars();
        refreshTurnLabel();
        refreshSkillButtons(player1);
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

    private void refreshTurnLabel() {
        Entity active = isPlayer1Turn ? player1 : player2;
        String suffix = isPlayer1Turn ? "" : " (CPU)";
        turnLabel.setText(active.getName() + "'s turn" + suffix);
    }

    private void refreshSkillButtons(Entity active) {
        setActionButtonsEnabled(true);
        for (int i = 1; i <= 3; i++) {
            Skill skill    = active.getSkill(i);
            JButton btn    = i == 1 ? skill1Button : i == 2 ? skill2Button : skill3Button;
            boolean usable = !skill.isCooldown() && active.getCurrentMana() >= skill.getManaCost();
            btn.setText(skill.isCooldown()
                    ? skill.getName() + " (CD:" + skill.getCurrentCooldown() + ")"
                    : skill.getName() + " (" + skill.getManaCost() + "MP)");
            btn.setEnabled(usable);
        }
    }

    private void setActionButtonsEnabled(boolean enabled) {
        basicAttackButton.setEnabled(enabled);
        skill1Button.setEnabled(enabled);
        skill2Button.setEnabled(enabled);
        skill3Button.setEnabled(enabled);
    }

    @Override protected void hideButtons() { }
    @Override protected void showButtons()  { }
}