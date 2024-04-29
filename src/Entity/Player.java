package src.Entity;

import java.awt.Color;
import java.awt.Graphics2D;

import src.GameEngine.GamePanel;
import src.GameEngine.InputHandler;

public class Player extends Entity {

    GamePanel gamePanel;
    InputHandler inputHandler;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gPanel, InputHandler iHandler) {
        this.gamePanel = gPanel;
        this.inputHandler = iHandler;

        screenX = (gamePanel.screenWidth - gamePanel.tileSize) / 2;
        screenY = (gamePanel.screenHeight - gamePanel.tileSize) / 2;

        setDefaultValues();
    }

    public void setDefaultValues() {
        worldX = 3 * gamePanel.tileSize;
        worldY = 3 * gamePanel.tileSize;
        speed = 8;
    }

    public void update() {
        if (inputHandler.upPressed) {
            verticalSpeed = -speed;
        }
        if (inputHandler.downPressed) {
            verticalSpeed = speed;
        }
        if (inputHandler.leftPressed) {
            horizontalSpeed = -speed;
        }
        if (inputHandler.rightPressed) {
            horizontalSpeed = speed;
        }

        if (Math.abs(horizontalSpeed) > 0 && Math.abs(verticalSpeed) > 0) {
            horizontalSpeed = (int) Math.round((float) horizontalSpeed / 1.41);
            verticalSpeed = (int) Math.round((float) verticalSpeed / 1.41);
        }

        worldX += horizontalSpeed;
        worldY += verticalSpeed;

        horizontalSpeed = 0;
        verticalSpeed = 0;
    }

    public void draw(Graphics2D graphics2d) {
        graphics2d.setColor(Color.white);

        graphics2d.fillRect(worldX, worldY, gamePanel.tileSize, gamePanel.tileSize);
    }
}
