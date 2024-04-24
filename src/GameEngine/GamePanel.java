package src.GameEngine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import src.Entity.Player;

public class GamePanel extends JPanel implements Runnable {
    public final int tileSize = 48;
    final int screenWidth = 1200;
    final int screenHeight = 800;

    int FPS = 60;

    InputHandler inputHandler;

    Player player;

    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        inputHandler = new InputHandler();
        this.addKeyListener(inputHandler);
        this.setFocusable(true);

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

        player.draw(graphics2d);

        graphics2d.dispose();
    }
}
