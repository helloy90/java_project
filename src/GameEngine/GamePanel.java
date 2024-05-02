package src.GameEngine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import src.Entity.Player;
import src.Tile.TileManager;
import src.UI.UI;

public class GamePanel extends JPanel implements Runnable {
    public static final int tileSize = 48;
    public static final int screenWidth = 1200;
    public static final int screenHeight = 800;

    public static final int maxScreenColumns = screenWidth / tileSize;
    public static final int maxScreenRows = screenHeight / tileSize;

    public final int FPS = 60;

    public InputHandler inputHandler;
    public TileManager tileManager;
    UI ui;
    public RecordStorage recordStorage;

    public Player player;
    public boolean playerFinished;

    String[] maps;
    int nextMap;
    public int amountOfMaps;

    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        maps = new String[] {
                "/Resources/Maps/map01.txt",
                "/Resources/Maps/map02.txt",
                "/Resources/Maps/map03.txt"
};
        nextMap = 0;
        amountOfMaps = maps.length;

        inputHandler = new InputHandler();
        this.addKeyListener(inputHandler);
        this.setFocusable(true);

        tileManager = new TileManager(this);
        ui = new UI(this);
        recordStorage = new RecordStorage(this);

        player = new Player(this, inputHandler);
        playerFinished = false;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void loadNextMap() {
        if (nextMap < amountOfMaps) {
            tileManager.loadMap(maps[nextMap]);
            nextMap++;
            mapReset();
        }
    }

    public void mapReset() {
        ui.reset();
        player.reset();
        inputHandler.reset();
    }

    public void FinishGame() {
        playerFinished = true;
        ui.prepareFinishText();
    }

    @Override
    public void run() {
        loadNextMap();
        ui.reset();
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
        if (playerFinished) {
            return;
        }
        if (!player.dead) {
            player.update();
            if (player.escaped) {
                ui.getWinScreen();
            }
        } else {
            ui.getDeathScreen();
        }

        ui.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2d = (Graphics2D) g;

        if (playerFinished) {
            ui.drawFinish(graphics2d, recordStorage.mapsTime);
            return;
        }

        tileManager.draw(graphics2d);

        player.draw(graphics2d);

        ui.draw(graphics2d);

        graphics2d.dispose();
    }
}
