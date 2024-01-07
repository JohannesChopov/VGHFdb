package be.kuleuven.dbproject.model;

public class Museum extends Locatie{
    private int museumID;
    private String naam;
    private double inkomprijs;
    private String adres;

    public Museum(){

    }

    public Museum(int museumID, String naam, double inkomprijs, String adres){
        this.museumID = museumID;
        this.naam = naam;
        this.inkomprijs = inkomprijs;
        this.adres = adres;
    }

    @Override
    public String toString() {
        return "Museum{" + "museumID='" + museumID + "', naam='" + naam + "inkomprijs='" + inkomprijs + "adres='" + adres + "'}";
    }

    /*
    public int getMuseumID() {
        return museumID;
    }

     */

    public int getID() {
        return museumID;
    }

    public void setMuseumID(int museumID) {
        this.museumID = museumID;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public double getInkomprijs() {
        return inkomprijs;
    }

    public void setInkomprijs(double inkomprijs) {
        this.inkomprijs = inkomprijs;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
}
