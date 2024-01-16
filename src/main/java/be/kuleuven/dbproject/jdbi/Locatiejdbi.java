package be.kuleuven.dbproject.jdbi;

import be.kuleuven.dbproject.model.Locatie;
import be.kuleuven.dbproject.model.Warenhuis;

import java.util.List;

public class Locatiejdbi {
    private final Warenhuisjdbi warenhuisjdbi;
    private final Museumjdbi museumjdbi;

    public Locatiejdbi(Warenhuisjdbi warenhuisjdbi, Museumjdbi museumjdbi) {
        this.warenhuisjdbi = warenhuisjdbi;
        this.museumjdbi = museumjdbi;
    }

    //List<T> getAll();
}
