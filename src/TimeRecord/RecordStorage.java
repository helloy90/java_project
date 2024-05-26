package src.TimeRecord;

import src.GameEngine.GamePanel;

public class RecordStorage {
    public String playerName = "player1";
    public double[] mapsTime;
    GamePanel gamePanel;

    public double timeOnCurrentMap;
    ServerRecord serverRecord;

    public RecordStorage(GamePanel gPanel) {
        this.gamePanel = gPanel;
        mapsTime = new double[gamePanel.amountOfMaps];
    }

    public void saveTimeOnMap(int currentMap, double time) {
        mapsTime[currentMap] = time;
    }

    public void timeReset() {
        timeOnCurrentMap = 0;
    }

    public void update() {
        timeOnCurrentMap += (double) 1 / gamePanel.FPS;
    }

    public void sendTimeToServer() {
        ServerRecord serverRecord = new ServerRecord(playerName, mapsTime);
        serverRecord.start();
    }
}
