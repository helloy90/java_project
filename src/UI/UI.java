package src.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.ArrayList;

import src.GameEngine.GamePanel;

public class UI {
    GamePanel gamePanel;
    Font arial_40;
    double timeOnCurrentMap;
    DecimalFormat dFormat;
    public boolean playerDead;
    public boolean playerEscaped;
    int currentMap;

    String deathLine;
    ArrayList<String> finishText;

    public UI(GamePanel gPanel) {
        this.gamePanel = gPanel;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        dFormat = new DecimalFormat("#0.000");

        playerDead = false;
        playerEscaped = false;

        currentMap = 0;

        deathLine = "You died! Press R to restart.";
        finishText = new ArrayList<String>();
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

    public void prepareFinishText() {

        finishText.add("Congratulations, you have completed the game! ");
        finishText.add("Here is your results:");
        for (int i = 1; i <= gamePanel.amountOfMaps; i++) {
            finishText.add("Map " + i + " - " + dFormat.format(gamePanel.recordStorage.mapsTime[i - 1]) + "s");
        }
    }

    public void update() {
        if (!playerDead) {
            if (playerEscaped) {
                gamePanel.recordStorage.saveTimeOnMap(currentMap, timeOnCurrentMap);
                gamePanel.loadNextMap();
                currentMap++;
                if (currentMap == gamePanel.amountOfMaps) {
                    gamePanel.FinishGame();
                }
            }
        } else if (gamePanel.inputHandler.canRestart) {
            gamePanel.mapReset();
        }
    }

    public void draw(Graphics2D graphics2d) {
        if (!playerDead) {
            graphics2d.setFont(arial_40);
            graphics2d.setColor(Color.WHITE);
            timeOnCurrentMap += (double) 1 / gamePanel.FPS;
            graphics2d.drawString("Time: " + dFormat.format(timeOnCurrentMap), 50, 50);
            // graphics2d.drawString("Overall time: " + gamePanel.player.timeOnCurrentTry,
            // 0, 0);
        } else {
            graphics2d.setFont(arial_40);
            graphics2d.setColor(Color.RED);
            int textLength = (int) graphics2d.getFontMetrics().getStringBounds(deathLine, graphics2d).getWidth();
            graphics2d.drawString(deathLine, GamePanel.screenWidth / 2 - textLength / 2,
                    GamePanel.screenHeight - GamePanel.tileSize * 3);
        }
    }

    public void drawFinish(Graphics2D graphics2d, double[] mapsTime) {
        graphics2d.setFont(arial_40);
        graphics2d.setColor(Color.GREEN);
        int currentLine = 0;
        for (String line : finishText) {
            int lineLength = (int) graphics2d.getFontMetrics().getStringBounds(line, graphics2d).getWidth();
            graphics2d.drawString(line, GamePanel.screenWidth / 2 - lineLength / 2,
                    GamePanel.screenHeight - GamePanel.tileSize * (finishText.size() - currentLine));
            currentLine++;
        }
    }
}
