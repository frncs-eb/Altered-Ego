package entity;

import util.Util;

public class Entity {
    protected final String name;
    protected final int baseHP = 500;
    protected int currentHP;
    protected final int baseMana = 200;
    protected int currentMana;

    protected Skill skill1;
    protected Skill skill2;
    protected Skill skill3;

    public Entity(String name, String skill1Name, String skill2Name, String skill3Name) {
        this.name = name;
        this.currentHP = baseHP;
        this.currentMana = baseMana;
        skill1 = new Skill(skill1Name, 50, 60, 25, 1);
        skill2 = new Skill(skill2Name, 60, 70, 50, 2);
        skill3 = new Skill(skill3Name, 100, 150, 100, 3);
    }

    public void basicAttack(Entity target) {
        int damage = Util.rng(40, 50);
        target.takeDamage(damage);
    }

    public void takeDamage(int damage) {
        if(isAlive()) {
            currentHP = Math.clamp(currentHP - damage, 0, baseHP);
        }
    }

    public void regenHP() {
        if(isAlive()) {
            currentMana = Math.clamp(currentHP + 50, 0, baseMana);
        }
    }

    public void regenMana() {
        if(isAlive()) {
            currentMana = Math.clamp(currentMana + 20, 0, baseMana);
        }
    }

    public void useSkill(int skillIndex, Entity target) {
        Skill selectedSkill = switch(skillIndex) {
            case 1 -> skill1;
            case 2 -> skill2;
            case 3 -> skill3;
            default -> null;
        };

        if(selectedSkill != null) {
            int damage = selectedSkill.useSkill();
            target.takeDamage(damage);
            currentMana -= selectedSkill.getManaCost();
        }
    }

    public void reduceCooldowns() {
        skill1.reduceCooldown();
        skill2.reduceCooldown();
        skill3.reduceCooldown();
    }

    public void resetCharacter() {
        currentHP = baseHP;
        currentMana = baseMana;
        skill1.resetCooldown();
        skill2.resetCooldown();
        skill3.resetCooldown();
    }

    public boolean isAlive() {
        return currentHP > 0;
    }

    public String getName() {
        return name;
    }

    public int getBaseHP() {
        return baseHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getBaseMana() {
        return baseMana;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public Skill getSkill(int index) {
        return switch(index) {
            case 1 -> skill1;
            case 2 -> skill2;
            case 3 -> skill3;
            default -> null;
        };
    }

    //for arcade
    public void healHP(int amount) {
        currentHP = Math.min(currentHP + amount, baseHP);
    }

    //for arcade
    public void healMana(int amount) {
        currentMana = Math.min(currentMana + amount, baseMana);
    }
}
