package be.kuleuven.dbproject.model;

public class Warenhuis {
    private int warenhuisID;
    String naam;
    String adres;

    public Warenhuis(){

    }

    public Warenhuis(int warenhuisID, String naam, String adres){
        this.warenhuisID = warenhuisID;
        this.naam = naam;
        this.adres = adres;
    }

    @Override
    public String toString() {
        return "Warenhuis{" + "warenhuisID='" + warenhuisID + "', naam='" + naam + "', adres='" + adres +  "'}";
    }

    public int getWarenhuisID() {
        return warenhuisID;
    }

    public void setWarenhuisID(int warenhuisID) {
        this.warenhuisID = warenhuisID;
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
