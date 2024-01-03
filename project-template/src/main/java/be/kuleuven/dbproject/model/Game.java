package be.kuleuven.dbproject.model;

public class Game {
    private int gameID;
    String titel;
    String genre;

    public Game(){

    }

    public Game(int gameID, String titel, String genre){
        this.gameID = gameID;
        this.titel = titel;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Game{" + "gameID='" + gameID + "', titel='" + titel + "', genre='" + genre +  "'}";
    }

    public String getTitel(){
        return titel;
    }

    public int getGameID(){
        return gameID;
    }

    public String getGenre() {
        return genre;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
