package src.Player;

import java.awt.Graphics2D;

import src.GameEngine.GamePanel;
import src.GameEngine.InputHandler;

public class Player {
    public PlayerModel playerModel;
    PlayerController playerController;
    PlayerView playerView;

    public Player(GamePanel gPanel, InputHandler iHandler) {

        playerModel = new PlayerModel();

        playerController = new PlayerController(playerModel, iHandler, gPanel);

        playerView = new PlayerView(playerModel);

        playerController.reset();
    }

    public void reset() {
        playerController.reset();
    }


    public void update() {
        playerController.update();
    }

    public void draw(Graphics2D graphics2d) {
        playerView.draw(graphics2d);
    }
}
