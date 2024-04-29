package src.Tile;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import src.GameEngine.GamePanel;

public class TileManager {
    GamePanel gamePanel;
    Tile[] tileTypes;
    int tileMap[][];

    public TileManager(GamePanel gPanel) {
        this.gamePanel = gPanel;
        tileTypes = new Tile[10];
        getTileImage();
        loadMap("/Resources/Maps/map01.txt");
    }

    public void getTileImage() {
        tileTypes[0] = new Tile("Floor", false);

        tileTypes[1] = new Tile("Barier", true);

        tileTypes[2] = new Tile("DamageTile", true);

        tileTypes[3] = new Tile("EndTile", true);
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
        int x = 0;
        int y = 0;

        for (int[] row : tileMap) {
            for (int tileNum : row) {
                graphics2d.drawImage(tileTypes[tileNum].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
                x += gamePanel.tileSize;
            }
            x = 0;
            y += gamePanel.tileSize;
        }
    }
}
