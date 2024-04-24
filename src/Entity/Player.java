package src.Entity;

import java.awt.Color;
import java.awt.Graphics2D;

import src.GameEngine.GamePanel;
import src.GameEngine.InputHandler;

public class Player extends Entity {

    GamePanel gamePanel;
    InputHandler inputHandler;

    public Player(GamePanel gPanel, InputHandler iHandler) {
        this.gamePanel = gPanel;
        this.inputHandler = iHandler;

        setDefaultValues();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
    }
    
    public void update() {
        if (inputHandler.upPressed) {
            y -= speed;
        } else if (inputHandler.downPressed) {
            y += speed;
        } else if (inputHandler.leftPressed) {
            x -= speed;
        } else if (inputHandler.rightPressed) {
            x += speed;
        }
    }

    public void draw(Graphics2D graphics2d) {
        graphics2d.setColor(Color.white);

        graphics2d.fillRect(x, y, gamePanel.tileSize, gamePanel.tileSize);
    }
}
