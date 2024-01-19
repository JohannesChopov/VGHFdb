package be.kuleuven.dbproject.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Platform platform = (Platform) o;
        return platformID == platform.platformID && Objects.equals(naam, platform.naam);
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
