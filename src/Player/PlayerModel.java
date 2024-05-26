package src.Player;

import src.Entity.Entity;
import src.GameEngine.GamePanel;

public class PlayerModel extends Entity {
    public boolean dead;
    public boolean escaped;
    public boolean finished;

    public final int sizeX;
    public final int sizeY;

    public final int screenX;
    public final int screenY;

    public PlayerModel() {
        dead = false;
        escaped = false;
        finished = false;

        sizeX = GamePanel.tileSize;
        sizeY = GamePanel.tileSize;

        screenX = (GamePanel.screenWidth - GamePanel.tileSize) / 2;
        screenY = (GamePanel.screenHeight - GamePanel.tileSize) / 2;
    }

}
