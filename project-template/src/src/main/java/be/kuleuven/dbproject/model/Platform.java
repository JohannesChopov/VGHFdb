package be.kuleuven.dbproject.model;

public class Platform {
    private int platformID;
    String naam;

    public Platform(){

    }

    public Platform(String naam){
        this.naam = naam;
    }

    @Override
    public String toString() {
        return "Platform " +  platformID + " = " + naam;
    }

    public int getPlatformID() {
        return platformID;
    }

    public void setPlatformID(int platformID) {
        this.platformID = platformID;
    }

    public String getNaam(){
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }
}
