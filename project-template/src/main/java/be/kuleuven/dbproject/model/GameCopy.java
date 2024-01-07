package be.kuleuven.dbproject.model;

public class GameCopy {
    private int gamecopyID;
    int gameplatformID;
    Integer museumID;
    Integer warenhuisID;

    public GameCopy() {

    }

    public GameCopy(int gameplatformID, Integer museumID, Integer warenhuisID){
        //this.gamecopyID = gamecopyID;
        this.gameplatformID = gameplatformID;
        this.museumID = museumID;
        this.warenhuisID = warenhuisID;
    }

    @Override
    public String toString() {
        return "GameCopy{" +
                "gamecopyID='" + gamecopyID + '\'' +
                ", gameplatformID='" + gameplatformID + '\'' +
                ", museumID='" + museumID + '\'' +
                ", warenhuisID='" + warenhuisID + '\'' +
                '}';
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

    public Integer getMuseumID() {
        return museumID;
    }

    public Integer getWarenhuisID() {
        return warenhuisID;
    }

    public int getPlaatsID() {
        if (museumID != null) {
            return museumID;
        } else if (warenhuisID != null) {
            return warenhuisID;
        } else {
            // Handle the case when both museumID and warenhuisID are null
            // You might want to throw an exception, return a default value, or handle it in a way that makes sense for your application
            throw new IllegalStateException("Both museumID and warenhuisID are null.");
        }
    }

    public void setGamecopyID(int gamecopyID) {
        this.gamecopyID = gamecopyID;
    }

    public void setGameplatformID(int gameplatformID) {
        this.gameplatformID = gameplatformID;
    }

    public void setMuseumID(Integer museumID) {
        this.museumID = museumID;
    }

    public void setWarenhuisID(Integer warenhuisID) {
        this.warenhuisID = warenhuisID;
    }
}
