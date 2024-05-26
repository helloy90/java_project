package src.GameEngine;

import java.awt.Graphics2D;

import src.Player.Player;
import src.Tile.TileManager;

public class Camera {
    Player player;
    TileManager tileManager;

    Camera(Player pl, TileManager tManager) {
        player = pl;
        tileManager = tManager;
    }

    public void draw(Graphics2D graphics2d) {
        int worldCol = 0;
        int worldRow = 0;

        int worldX = 0;
        int worldY = 0;

        int screenX = 0;
        int screenY = 0;

        for (int[] row : tileManager.tileMap) {
            for (int tileNum : row) {

                worldX = worldCol * GamePanel.tileSize;
                worldY = worldRow * GamePanel.tileSize;
                // Correct camera placement
                screenX = worldX - player.playerModel.worldX + player.playerModel.screenX;
                screenY = worldY - player.playerModel.worldY + player.playerModel.screenY;
                
                if (worldX + GamePanel.tileSize > player.playerModel.worldX - player.playerModel.screenX
                        && worldX - GamePanel.tileSize < player.playerModel.worldX + player.playerModel.screenX
                        && worldY + GamePanel.tileSize > player.playerModel.worldY - player.playerModel.screenY
                        && worldY - GamePanel.tileSize < player.playerModel.worldY + player.playerModel.screenY) {
                    graphics2d.drawImage(tileManager.tileTypes[tileNum].image, screenX, screenY, GamePanel.tileSize,
                            GamePanel.tileSize,
                            null);
                }

                worldCol++;
            }
            worldCol = 0;
            worldRow++;
        }
    }
}
