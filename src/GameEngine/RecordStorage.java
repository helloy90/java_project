package src.GameEngine;

public class RecordStorage {
    public double[] mapsTime;
    GamePanel gamePanel;

    public RecordStorage(GamePanel gPanel) {
        this.gamePanel = gPanel;
        mapsTime = new double[gamePanel.amountOfMaps];
    }

    public void saveTimeOnMap(int currentMap, double time) {
        mapsTime[currentMap] = time;
    }
}
