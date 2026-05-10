package graphic;

import entity.AnimationState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;

/**
 * Pure sprite loader and renderer — NOT a JPanel.
 *
 * Each character has ONE sprite sheet where each row is a different action.
 * Row order matches AnimationState.values():
 *   row 0 → IDLE
 *   row 1 → TAKE_DAMAGE
 *   row 2 → BASIC_ATTACK
 *   row 3 → SKILL_1
 *   row 4 → SKILL_2
 *   row 5 → SKILL_3
 *
 * Flipping:
 *   Call setFlipped(true) for player 2 so the sprite faces left (toward player 1).
 *   The flip is applied at draw time — no extra images needed.
 *
 * Usage:
 *   Graphic g = new Graphic();
 *   g.setFlipped(true);  // for player 2
 *   g.loadAllAnimations("/sprites/cosmic_dasel.png", 64, 64,
 *       new int[]{ 4, 3, 5, 6, 6, 6 });
 *
 *   // In paintComponent:
 *   g.update();
 *   g.draw(graphics, x, y, width, height);
 *
 *   // Trigger animations:
 *   g.playOnce(AnimationState.BASIC_ATTACK); // plays once then returns to IDLE
 *   g.setState(AnimationState.IDLE);          // switch to a looping state
 */
public class Graphic {

    // ── Config ─────────────────────────────────────────────────────────────
    private int     animationSpeed = 8;    // ticks per frame — lower = faster
    private boolean flipped        = false; // true = mirror horizontally (player 2)

    // ── State ──────────────────────────────────────────────────────────────
    private final Map<AnimationState, BufferedImage[]> animations
            = new EnumMap<>(AnimationState.class);

    private AnimationState currentState = AnimationState.IDLE;
    private int     aniTick  = 0;
    private int     aniIndex = 0;
    private boolean oneShot  = false;

    // ── Loading ────────────────────────────────────────────────────────────

    /**
     * Load all animation states from one 2D sprite sheet.
     *
     * The sheet is read once and each row is sliced into frames.
     * frameCounts.length must equal AnimationState.values().length.
     *
     * @param path        classpath path, e.g. "/sprites/cosmic_dasel.png"
     * @param frameWidth  width of a single frame in pixels
     * @param frameHeight height of a single row in pixels
     * @param frameCounts frame count per row in AnimationState enum order
     *                    e.g. new int[]{ 4, 3, 5, 6, 6, 6 }
     *                    → idle=4, takeDmg=3, basicAtk=5, sk1=6, sk2=6, sk3=6
     */
    public void loadAllAnimations(String path,
                                  int frameWidth,
                                  int frameHeight,
                                  int[] frameCounts) {
        AnimationState[] states = AnimationState.values();

        if (frameCounts.length != states.length) {
            System.err.println("[Graphic] loadAllAnimations: frameCounts length ("
                    + frameCounts.length + ") must match AnimationState count ("
                    + states.length + ") — sheet: " + path);
            return;
        }

        // Read the sheet once, slice every row from the same BufferedImage
        BufferedImage sheet;
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) throw new IOException("Sprite sheet not found: " + path);
            sheet = ImageIO.read(is);
        } catch (IOException e) {
            System.err.println("[Graphic] Could not read sheet: " + path + " — " + e.getMessage());
            return;
        }

        for (int row = 0; row < states.length; row++) {
            int count = frameCounts[row];
            BufferedImage[] frames = new BufferedImage[count];
            for (int col = 0; col < count; col++) {
                frames[col] = sheet.getSubimage(
                        col * frameWidth,  // x — advances per frame
                        row * frameHeight, // y — one row per action
                        frameWidth,
                        frameHeight);
            }
            animations.put(states[row], frames);
        }
    }

    /**
     * Load a single animation state from a horizontal sprite strip (one row).
     * Use this for backgrounds or other single-purpose sheets.
     */
    public void loadStrip(String path, int frameWidth, int frameHeight, int frameCount) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) throw new IOException("Strip not found: " + path);
            BufferedImage sheet = ImageIO.read(is);
            BufferedImage[] frames = new BufferedImage[frameCount];
            for (int col = 0; col < frameCount; col++) {
                frames[col] = sheet.getSubimage(col * frameWidth, 0, frameWidth, frameHeight);
            }
            animations.put(AnimationState.IDLE, frames);
        } catch (IOException e) {
            System.err.println("[Graphic] Could not read strip: " + path + " — " + e.getMessage());
        }
    }

    // ── State control ──────────────────────────────────────────────────────

    /** Switch to a looping state (e.g. IDLE). Restarts from frame 0 if state changes. */
    public void setState(AnimationState state) {
        if (currentState == state) return;
        currentState = state;
        aniTick  = 0;
        aniIndex = 0;
        oneShot  = false;
    }

    /**
     * Play a one-shot animation (e.g. BASIC_ATTACK, TAKE_DAMAGE).
     * Automatically returns to IDLE when the last frame is reached.
     * Ignored if the same one-shot is already playing.
     */
    public void playOnce(AnimationState state) {
        if (oneShot && currentState == state) return;
        currentState = state;
        aniTick  = 0;
        aniIndex = 0;
        oneShot  = true;
    }

    // ── Per-frame logic ────────────────────────────────────────────────────

    /** Advance animation by one tick. Call once per repaint from paintComponent. */
    public void update() {
        BufferedImage[] frames = animations.get(currentState);
        if (frames == null || frames.length == 0) return;

        aniTick++;
        if (aniTick < animationSpeed) return;

        aniTick = 0;
        aniIndex++;

        if (aniIndex >= frames.length) {
            if (oneShot) {
                oneShot = false;
                setState(AnimationState.IDLE);
            } else {
                aniIndex = 0;
            }
        }
    }

    /**
     * Draw the current frame at the given screen coordinates.
     * If flipped is true the image is mirrored horizontally so player 2
     * faces left toward player 1. No extra images needed — the flip is
     * applied via a Graphics2D transform at draw time.
     */
    public void draw(Graphics g, int x, int y, int width, int height) {
        BufferedImage[] frames = animations.get(currentState);
        if (frames == null || aniIndex >= frames.length) return;

        if (flipped) {
            // Mirror horizontally: translate to x+width, scale x by -1
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.translate(x + width, y);
            g2d.scale(-1, 1);
            g2d.drawImage(frames[aniIndex], 0, 0, width, height, null);
            g2d.dispose();
        } else {
            g.drawImage(frames[aniIndex], x, y, width, height, null);
        }
    }

    // ── Accessors ──────────────────────────────────────────────────────────

    /**
     * Set to true for player 2 so the sprite faces left toward player 1.
     * Can be called before or after loading animations.
     */
    public void setFlipped(boolean flipped)  { this.flipped = flipped; }
    public boolean isFlipped()               { return flipped; }

    public AnimationState getCurrentState()  { return currentState; }
    public boolean        isOneShot()        { return oneShot; }
    public boolean        hasAnimation(AnimationState state) { return animations.containsKey(state); }

    /** Ticks per frame — lower is faster. Default is 8. */
    public void setAnimationSpeed(int speed) { this.animationSpeed = Math.max(1, speed); }
    public int  getAnimationSpeed()          { return animationSpeed; }
}