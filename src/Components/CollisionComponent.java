package src.Components;

import src.GameEngine.GamePanel;
import src.Tile.TileType;

public class CollisionComponent {
    public boolean certainTile(int x, int y, int[][] tileMap, TileType tileType) {
        int tileX = x / GamePanel.tileSize;
        int tileY = y / GamePanel.tileSize;

        int currentTileType = tileMap[tileY][tileX];

        if (currentTileType == tileType.ordinal()) {
            return true;
        }
        return false;
    }

    public boolean tileTypeMet(int x, int y, int width, int height, int[][] tileMap, TileType tileType) {
        if (certainTile(x, y, tileMap, tileType)) {
            return true;
        }
        if (certainTile(x + width, y, tileMap, tileType)) {
            return true;
        }
        if (certainTile(x + width, y + height, tileMap, tileType)) {
            return true;
        }
        if (certainTile(x, y + height, tileMap, tileType)) {
            return true;
        }
        return false;
    }
}
