package src.Player;

import java.awt.Color;
import java.awt.Graphics2D;

public class PlayerView {

    PlayerModel playerModel;

    PlayerView(PlayerModel player) {
        playerModel = player;
    }

    void draw(Graphics2D graphics2d) {
        graphics2d.setColor(Color.white);

        graphics2d.fillRect(playerModel.screenX, playerModel.screenY, playerModel.sizeX, playerModel.sizeY);

        // collider
        graphics2d.setColor(Color.blue);
        graphics2d.drawRect(playerModel.screenX, playerModel.screenY, playerModel.sizeX, playerModel.sizeY);
    }
}
