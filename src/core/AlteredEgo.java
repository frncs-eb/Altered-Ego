package core;

import screen.Screen;

public class AlteredEgo implements Runnable {
    private static final int FPS_LIMIT = 120;
    private static final double NANOSECONDS_PER_FRAME = 1_000_000_000.0 / FPS_LIMIT;

    private final GameWindow gameWindow;
    private final Screen screen;

    private Thread gameThread;
    private volatile boolean running = false;

    public AlteredEgo() {
        gameWindow = new GameWindow();
        screen = new Screen(gameWindow);

        startGame();
    }

    public synchronized void startGame() {
        if(running) return;

        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public synchronized void stopGame() {
        running = false;
    }

    @Override
    public void run() {
        int frame = 0;
        long lastFrame = System.nanoTime();
        long lastCheck = System.currentTimeMillis();

        while(running) {
            long now = System.nanoTime();
            if(now - lastFrame >= NANOSECONDS_PER_FRAME) {
                gameWindow.repaint();
                lastFrame = now;
                frame++;
            }

            //FPS Counter
            if(System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frame);
                frame = 0;
            }
        }
    }
}
