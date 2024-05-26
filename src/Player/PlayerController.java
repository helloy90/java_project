package src.Player;

import src.Components.CollisionComponent;
import src.Components.MovementComponent;
import src.GameEngine.GamePanel;
import src.GameEngine.InputHandler;
import src.Tile.TileType;

public class PlayerController {
    GamePanel gamePanel;
    MovementComponent movementComponent;
    CollisionComponent collisionComponent;

    PlayerModel playerModel;

    PlayerController(PlayerModel player, InputHandler iHandler, GamePanel gPanel) {
        playerModel = player;
        gamePanel = gPanel;

        movementComponent = new MovementComponent(iHandler);
        collisionComponent = new CollisionComponent();

        reset();
    }


    void update() {
        
        movementComponent.update();

        if (!collisionComponent.tileTypeMet(playerModel.worldX + movementComponent.getHorizontalSpeed(),
        playerModel.worldY + movementComponent.getVerticalSpeed(), playerModel.sizeX,
        playerModel.sizeY, gamePanel.tileManager.tileMap, TileType.Barier)) {
            playerModel.worldX += movementComponent.getHorizontalSpeed();
            playerModel.worldY += movementComponent.getVerticalSpeed();

            if (collisionComponent.tileTypeMet(playerModel.worldX, playerModel.worldY, playerModel.sizeX, playerModel.sizeY, gamePanel.tileManager.tileMap,
                    TileType.DamageTile)) {
                playerDied();
            } else if (collisionComponent.tileTypeMet(playerModel.worldX, playerModel.worldY, playerModel.sizeX, playerModel.sizeY, gamePanel.tileManager.tileMap,
                    TileType.EndTile)) {
                playerEscaped();
            }
        }

        movementComponent.resetSpeed();
    }

    public void setSpawnPoint() {
        playerModel.worldX = 2 * GamePanel.tileSize;
        playerModel.worldY = 2 * GamePanel.tileSize;
    }

    public void reset() {
        playerModel.dead = false;
        playerModel.escaped = false;
        playerModel.finished = false;
        setSpawnPoint();
    }

    public void playerDied() {
        playerModel.dead = true;
    }

    public void playerEscaped() {
        playerModel.escaped = true;
    }

}
