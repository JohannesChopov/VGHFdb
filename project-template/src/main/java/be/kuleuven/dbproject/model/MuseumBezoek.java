package be.kuleuven.dbproject.model;

import java.time.LocalDateTime;

public class MuseumBezoek {
    private int museumbezoekID;
    private int museumID;
    private int bezoekerID;
    private String tijdsstip;

    public MuseumBezoek() {

    }

    public MuseumBezoek(int museumID, int bezoekerID, String tijdsstip) {
        this.museumID = museumID;
        this.bezoekerID = bezoekerID;
        this.tijdsstip = tijdsstip;
    }

    @Override
    public String toString() {
        return "MuseumBezoek{" +
                "museumbezoekID='" + museumbezoekID + '\'' +
                ", museumID='" + museumID + '\'' +
                ", bezoekerID='" + bezoekerID + '\'' +
                ", tijdsstip='" + tijdsstip + '\'' +
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

    public String getTijdsstip() {
        return tijdsstip;
    }

    public void setTijdsstip(String tijdsstip) {
        this.tijdsstip = tijdsstip;
    }
}
