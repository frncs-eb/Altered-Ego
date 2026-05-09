package entity;

/**
 * Animation states in row order matching the sprite sheet:
 *   row 0 → IDLE
 *   row 1 → TAKE_DAMAGE
 *   row 2 → BASIC_ATTACK
 *   row 3 → SKILL_1
 *   row 4 → SKILL_2
 *   row 5 → SKILL_3
 */
public enum AnimationState {
    IDLE,
    TAKE_DAMAGE,
    BASIC_ATTACK,
    SKILL_1,
    SKILL_2,
    SKILL_3
}