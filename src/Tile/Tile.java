package src.Tile;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile {
    public BufferedImage image;
    public boolean collision;

    String imagesFilePath = "/Resources/Tiles/";
    String imageType = ".png";

    public Tile(String name, boolean coll) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagesFilePath + name + imageType));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = coll;
    }

    public void ChangeCollision(boolean coll) {
        collision = coll;
    }
}
