package be.kuleuven.dbproject.model;

public class MuseumBezoek {
    private int museumbezoekID;
    private int museumID;
    private int bezoekerID;
    private String datum;

    public MuseumBezoek() {

    }

    public MuseumBezoek(int museumID, int bezoekerID, String datum) {
        this.museumID = museumID;
        this.bezoekerID = bezoekerID;
        this.datum = datum;
    }

    @Override
    public String toString() {
        return "MuseumBezoek{" +
                "museumbezoekID='" + museumbezoekID + '\'' +
                ", museumID='" + museumID + '\'' +
                ", bezoekerID='" + bezoekerID + '\'' +
                ", tijdsstip='" + datum + '\'' +
                '}';
    }

    public int getMuseumbezoekID() {
        return museumbezoekID;
    }

    public void setMuseumbezoekID(int museumbezoekID) {
        this.museumbezoekID = museumbezoekID;
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

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}
