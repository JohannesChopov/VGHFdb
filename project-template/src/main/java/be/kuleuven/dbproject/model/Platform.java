package be.kuleuven.dbproject.model;

public class Platform {
    private int platformID;
    String naam;

    public Platform(){

    }

    public Platform(int platformID, String naam){
        this.platformID = platformID;
        this.naam = naam;
    }

    @Override
    public String toString() {
        return "Platform{" + "platformID='" + platformID+ "', naam='" + naam + "'}";
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
