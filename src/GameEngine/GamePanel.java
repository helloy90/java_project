package src.GameEngine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import src.Entity.Player;
import src.Tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    public final int tileSize = 48;
    public final int screenWidth = 1200;
    public final int screenHeight = 800;

    public final int maxScreenColumns = screenWidth / tileSize;
    public final int maxScreenRows = screenHeight / tileSize;

    int FPS = 60;

    InputHandler inputHandler;
    TileManager tileManager;

    Player player;

    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.PINK);
        this.setDoubleBuffered(true);

        inputHandler = new InputHandler();
        this.addKeyListener(inputHandler);
        this.setFocusable(true);

        tileManager = new TileManager(this);

        player = new Player(this, inputHandler);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        int secToNanoSec = 1000000000;

        double drawInterval = secToNanoSec / FPS;
        double nextFrame = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            update();

            repaint();

            try {
                double remainingTime = nextFrame - System.nanoTime();

                remainingTime /= 1000000;
                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextFrame += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {

        player.update();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2d = (Graphics2D) g;

        tileManager.draw(graphics2d);

        player.draw(graphics2d);

        graphics2d.dispose();
    }
}
