package entity;

import db.DatabaseHandler;
import db.Tables;
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


    public boolean deleteGrupa(String nume_grupa) {
        if (db.ifExistsElevInGroup(nume_grupa)) {
            System.out.println("Exista cel putin un elev in grupa indicata.");
            return false;
        } else {
            return db.deleteValue(Tables.Grupe, "nume_grupa", nume_grupa);
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

    @Override
    public String toString() {
        return "Nume grupa: " + nume_grupa;
    }
}
