package be.kuleuven.dbproject.model;

public class Locatie {
    private int locatieID;
    private String naam;
    private String adres;

    public Locatie() {
    }

    public Locatie(int locatieID, String naam, String adres) {
        this.locatieID = locatieID;
        this.naam = naam;
        this.adres = adres;
    }

    @Override
    public String toString() {
        return "Locatie{" +
                "locatieID=" + locatieID +
                ", naam='" + naam + '\'' +
                ", adres='" + adres + '\'' +
                '}';
    }

    public int getID() {
        return locatieID;
    }

    public void setID(int locatieID) {
        this.locatieID = locatieID;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
}
