package be.kuleuven.dbproject.model;

public class Bezoeker {
    private int bezoekerID;
    private int museumID;
    String naam;

    public Bezoeker(){

    }

    public Bezoeker(int bezoekerID, int museumID, String naam) {
        this.bezoekerID = bezoekerID;
        this.museumID = museumID;
        this.naam = naam;
    }

    @Override
    public String toString() {
        return "Bezoeker{" + "bezoekerID=" + bezoekerID + ", museumID='" + museumID  + ", naam='" + naam  + "'}";
    }

    public int getMuseumID() {
        return museumID;
    }

    public void setMuseumID(int museumID) {
        this.museumID = museumID;
    }

    public int getBezoekerID() {
        return bezoekerID;
    }

    public void setBezoekerID(int bezoekerID) {
        this.bezoekerID = bezoekerID;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }
}
