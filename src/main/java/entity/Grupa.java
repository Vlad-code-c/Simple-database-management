package entity;

import db.DatabaseHandler;
import main.Main;

import java.util.ArrayList;

public class Grupa {
    private String nume_grupa;
    private DatabaseHandler db;


    public Grupa(String nume_grupa) {
        this.nume_grupa = nume_grupa;

        db = Main.db;

    }

    public void addToDb() {
        if (db.insertValue(this)) {
            System.out.println("Grupa a fost adaugata cu succes!");
        } else {
            System.out.println("Grupa nu a fost adaugata!");
        }
    }

    public Grupa() {
        db = new DatabaseHandler();
    }

    public String getNume_grupa() {
        return nume_grupa;
    }

    /**
     * Selecteaza toate grupele din tabelul Grupe
     * @return an array with all groups
     * */
    public ArrayList<Grupa> getGrupe() {
        ArrayList<Grupa> grupe = (ArrayList<Grupa>) db.selectAll(this);

        return grupe;
    }

}
