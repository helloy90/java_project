package src.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

import src.GameEngine.GamePanel;

public class UI {
    GamePanel gamePanel;
    Font arial_40;
    double timeOnCurrentMap;
    DecimalFormat dFormat;
    public boolean playerDead;
    public boolean playerEscaped;
    int currentMap;

    public UI(GamePanel gPanel) {
        this.gamePanel = gPanel;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        dFormat = new DecimalFormat("#0.000");

        playerDead = false;
        playerEscaped = false;

        currentMap = 0;
    }

    public void reset() {
        timeOnCurrentMap = 0;
        playerDead = false;
        playerEscaped = false;
    }

    public void getDeathScreen() {
        playerDead = true;
    }

    public void getWinScreen() {
        playerEscaped = true;
    }

    public void update() {
        if (!playerDead) {
            if (playerEscaped) {
                gamePanel.recordStorage.saveTimeOnMap(currentMap, timeOnCurrentMap);
                gamePanel.loadNextMap();
                currentMap++;
            }
        } else if (gamePanel.inputHandler.canRestart) {
            gamePanel.mapReset();
        }
    }

    public void draw(Graphics2D graphics2d) {
        if (!playerDead) {
            graphics2d.setFont(arial_40);
            graphics2d.setColor(Color.WHITE);
            timeOnCurrentMap += (double)1 / gamePanel.FPS;
            graphics2d.drawString("Time: " + dFormat.format(timeOnCurrentMap), 50, 50);
            // graphics2d.drawString("Overall time: " + gamePanel.player.timeOnCurrentTry, 0, 0);
        } else {
            String text = "You died! Press R to restart.";
            graphics2d.setFont(arial_40);
            graphics2d.setColor(Color.RED);
            int textLength = (int)graphics2d.getFontMetrics().getStringBounds(text, graphics2d).getWidth();
            graphics2d.drawString(text, GamePanel.screenWidth / 2 - textLength / 2, GamePanel.screenHeight - GamePanel.tileSize * 3);
        }
    }
}
