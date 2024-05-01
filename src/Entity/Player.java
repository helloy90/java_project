package src.Entity;

import java.awt.Color;
import java.awt.Graphics2D;

import src.Components.CollisionComponent;
import src.Components.MovementComponent;
import src.GameEngine.GamePanel;
import src.GameEngine.InputHandler;
import src.Tile.TileType;

public class Player extends Entity {
    GamePanel gamePanel;
    public boolean dead;
    public boolean escaped;

    MovementComponent movementComponent;
    CollisionComponent collisionComponent;

    public final int sizeX;
    public final int sizeY;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gPanel, InputHandler iHandler) {
        this.gamePanel = gPanel;

        sizeX = GamePanel.tileSize;
        sizeY = GamePanel.tileSize;

        screenX = (GamePanel.screenWidth - GamePanel.tileSize) / 2;
        screenY = (GamePanel.screenHeight - GamePanel.tileSize) / 2;

        movementComponent = new MovementComponent(this, iHandler);
        collisionComponent = new CollisionComponent(this);

        reset();
    }

    public void setSpawnPoint() {
        worldX = 2 * GamePanel.tileSize;
        worldY = 2 * GamePanel.tileSize;
    }

    public void reset() {
        dead = false;
        escaped = false;
        setSpawnPoint();
    }

    public void playerDied() {
        dead = true;
    }

    public void playerEscaped() {
        escaped = true;
    }

    public void update() {

        movementComponent.update();

        if (!collisionComponent.tileTypeMet(worldX + movementComponent.getHorizontalSpeed(),
                worldY + movementComponent.getVerticalSpeed(), sizeX,
                sizeY, gamePanel.tileManager.tileMap, TileType.Barier)) {
            worldX += movementComponent.getHorizontalSpeed();
            worldY += movementComponent.getVerticalSpeed();

            if (collisionComponent.tileTypeMet(worldX, worldY, sizeX, sizeY, gamePanel.tileManager.tileMap,
                    TileType.DamageTile)) {
                playerDied();
            } else if (collisionComponent.tileTypeMet(worldX, worldY, sizeX, sizeY, gamePanel.tileManager.tileMap,
                    TileType.EndTile)) {
                playerEscaped();
            }
        }

        movementComponent.resetSpeed();
    }

    public void draw(Graphics2D graphics2d) {
        graphics2d.setColor(Color.white);

        graphics2d.fillRect(screenX, screenY, sizeX, sizeY);

        // collider
        graphics2d.setColor(Color.blue);
        graphics2d.drawRect(screenX, screenY, sizeX, sizeY);
    }
}
