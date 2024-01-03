package be.kuleuven.dbproject.model;

public class GamePlatform {
    private int gameplatformID;
    private int gameID;
    private int platformID;

    public GamePlatform(){

    }

    public GamePlatform(int gameplatformID, int gameID, int platformID){
        this.gameplatformID = gameplatformID;
        this.gameID = gameID;
        this.platformID = platformID;
    }

    @Override
    public String toString() {
        return "GamePlatform{" + "gameplatformID='" + gameplatformID + "', gameID='" + gameID + "', platformID='" + platformID + "'}";
    }

    public int getGameplatformID() {
        return gameplatformID;
    }

    public void setGameplatformID(int gameplatformID) {
        this.gameplatformID = gameplatformID;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getPlatformID() {
        return platformID;
    }

    public void setPlatformID(int platformID) {
        this.platformID = platformID;
    }
}
