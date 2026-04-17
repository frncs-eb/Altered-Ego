package graphic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Graphic extends JPanel {
    private BufferedImage spriteSheet;
    private BufferedImage[] frames;

    private final int aniSpeed = 20;
    private int aniTick = 0;
    private int aniIndex = 0;

    public Graphic() {
        setOpaque(false);
    }

    public BufferedImage importImage(String path) throws IOException {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) {
                throw new IOException("Resource not found: " + path);
            }
            return ImageIO.read(is);
        }
    }

    public void loadAnimation(String path, int frameCount, int frameWidth, int frameHeight) {
        try {
            spriteSheet = importImage(path);
            frames = new BufferedImage[frameCount];
            for (int i = 0; i < frameCount; i++) {
                frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load animation from: " + path, e);
        }
    }

    public void updateAnimation() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= frames.length) {
                aniIndex = 0;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateAnimation();
        g.drawImage(frames[aniIndex], 0, 0, 720, 720, null);
    }
}
