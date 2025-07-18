package src.GameEngine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import src.Player.Player;
import src.Tile.TileManager;
import src.TimeRecord.RecordStorage;
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

    Camera camera;

    String[] maps;
    int nextMap;
    public int amountOfMaps;

    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        inputHandler = new InputHandler();
        this.addKeyListener(inputHandler);
        this.setFocusable(true);

        loadMaps();

        tileManager = new TileManager(this);

        ui = new UI(this);
        recordStorage = new RecordStorage(this);

        player = new Player(this, inputHandler);
        camera = new Camera(player, tileManager);
    }

    void loadMaps() {
        maps = new String[] {
                "/Resources/Maps/map01.txt",
                "/Resources/Maps/map02.txt",
                "/Resources/Maps/map03.txt"
        };

        nextMap = 0;
        amountOfMaps = maps.length;
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
        } else if (nextMap == amountOfMaps) {
            FinishGame();
        }
    }

    public void mapReset() {
        recordStorage.timeReset();
        player.reset();
        inputHandler.reset();
    }

    public void FinishGame() {
        player.playerModel.finished = true;
        ui.prepareFinishText();
        recordStorage.sendTimeToServer();
    }

    @Override
    public void run() {
        loadNextMap();
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
        if (player.playerModel.finished) {
            return;
        }
        if (!player.playerModel.dead) {
            player.update();
            recordStorage.update();
            if (player.playerModel.escaped) {
                recordStorage.saveTimeOnMap(nextMap - 1, recordStorage.timeOnCurrentMap);
                loadNextMap();
            }
        }
        if (inputHandler.canRestart) {
            mapReset();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2d = (Graphics2D) g;

        if (player.playerModel.finished) {
            ui.drawFinish(graphics2d);
            graphics2d.dispose();
            return;
        }

        camera.draw(graphics2d);

        player.draw(graphics2d);

        ui.draw(graphics2d);

        graphics2d.dispose();
    }
}
