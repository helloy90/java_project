package src.Tile;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import src.GameEngine.GamePanel;

public class TileManager {
    GamePanel gamePanel;
    public Tile[] tileTypes;
    public int[][] tileMap;

    public TileManager(GamePanel gPanel) {
        this.gamePanel = gPanel;
        tileTypes = new Tile[10];
        getTiles();
    }

    public void getTiles() {
        tileTypes[0] = new Tile("Floor", false, TileType.None);

        tileTypes[1] = new Tile("Barier", true, TileType.Barier);

        tileTypes[2] = new Tile("DamageTile", true, TileType.DamageTile);

        tileTypes[3] = new Tile("EndTile", true, TileType.EndTile);
    }

    public void loadMap(String filepath) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(filepath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            ArrayList<String> lines = new ArrayList<String>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();

            tileMap = new int[lines.size()][lines.get(0).length()];

            int column = 0;
            int row = 0;

            for (String string : lines) {
                String numbers[] = string.split(" ");
                for (String number : numbers) {
                    tileMap[row][column] = Integer.parseInt(number);
                    column++;
                }
                column = 0;
                row++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2d) {
        int worldCol = 0;
        int worldRow = 0;

        int worldX = 0;
        int worldY = 0;

        int screenX = 0;
        int screenY = 0;

        for (int[] row : tileMap) {
            for (int tileNum : row) {

                worldX = worldCol * GamePanel.tileSize;
                worldY = worldRow * GamePanel.tileSize;
                // Correct camera placement
                screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
                screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
                
                if (worldX + GamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX
                        && worldX - GamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX
                        && worldY + GamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY
                        && worldY - GamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {
                    graphics2d.drawImage(tileTypes[tileNum].image, screenX, screenY, GamePanel.tileSize,
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