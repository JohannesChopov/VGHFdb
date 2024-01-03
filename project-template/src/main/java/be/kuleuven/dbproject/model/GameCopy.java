package be.kuleuven.dbproject.model;

public class GameCopy {
    private int gamecopyID;
    private int gameplatformID;
    private int museumID;
    private int warenhuisID;

    public GameCopy(){

    }

    public GameCopy(int gamecopyID, int gameplatformID, int museumID, int warenhuisID){
        this.gamecopyID = gamecopyID;
        this.gameplatformID = gameplatformID;
        this.museumID = museumID;
        this.warenhuisID = warenhuisID;
    }

    @Override
    public String toString() {
        return "GameCopy{" + "gamecopyID='" + gamecopyID + "', gameplatformID='" + gameplatformID + "', museumID='" + museumID + "', warenhuisID='" + warenhuisID + "'}";
    }

    public int getGamecopyID() {
        return gamecopyID;
    }

    /*
    public String getNameById(int id) {

    }
     */

    public int getGameplatformID() {
        return gameplatformID;
    }

    public int getMuseumID() {
        return museumID;
    }

    public int getWarenhuisID() {
        return warenhuisID;
    }

    public void setGamecopyID(int gamecopyID) {
        this.gamecopyID = gamecopyID;
    }

    public void setGameplatformID(int gameplatformID) {
        this.gameplatformID = gameplatformID;
    }

    public void setMuseumID(int museumID) {
        this.museumID = museumID;
    }

    public void setWarenhuisID(int warenhuisID) {
        this.warenhuisID = warenhuisID;
    }
}
