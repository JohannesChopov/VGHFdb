package be.kuleuven.dbproject.model;

public class Donatie {
    private int donatieID;
    private int museumID;
    private double som;
    String datum;

    public Donatie(){
    }

    public Donatie(int museumID, double som, String datum){
        this.museumID = museumID;
        this.som = som;
        this.datum = datum;
    }

    @Override
    public String toString() {
        return "Donatie{" + "donatieID='" + donatieID + "museumID='" + museumID + "', som='" + som + "datum='" + datum + "'}";
    }

    public int getDonatieID(){
        return donatieID;
    }

    public int getMuseumID(){
        return museumID;
    }

    public double getSom(){
        return som;
    }

    public String getDatum(){
        return datum;
    }

    public void setDatum(String datum){
        this.datum = datum;
    }

    public void setSom(double som){
        this.som = som;
    }

    public void setDonatieID(int donatieID){
        this.donatieID = donatieID;
    }

    public void setMuseumID(int museumID){
        this.museumID = museumID;
    }

}
