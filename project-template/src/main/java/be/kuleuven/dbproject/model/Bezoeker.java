package be.kuleuven.dbproject.model;

public class Bezoeker {
    private int bezoekerID;
    String naam;

    public Bezoeker(){

    }

    public Bezoeker(int museumID, String naam) {
        this.naam = naam;
    }

    public Bezoeker(String naam) {
        this.naam = naam;
    }

    @Override
    public String toString() {
        return "Bezoeker{" + "bezoekerID=" + bezoekerID + ", naam='" + naam  + "'}";
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
