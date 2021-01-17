package entity;

import db.DatabaseHandler;
import db.Tables;
import main.Main;

import java.util.ArrayList;

public class Obiect {
    private int id_obiect;
    private String nume_obiect;

    private DatabaseHandler db;
    private static Tables tb = Tables.Obiecte;
    private static String primaryKeyColumnName = "id_obiect";

    public Obiect(int id_obiect, String nume_obiect) {
        this.id_obiect = id_obiect;
        this.nume_obiect = nume_obiect;

        db = Main.db;
    }

    public Obiect(String nume_obiect) {
        db = Main.db;

        this.id_obiect = getLastId() + 1;
        this.nume_obiect = nume_obiect;

    }

    public void addToDb() {
        if (db.insertValue(this)) {
            System.out.println("Obiectul a fost adaugat cu succes");
        } else {
            System.out.println("Obiectul nu a fost adaugat!");
        }
    }

    private int getLastId() {
        return db.getLastId(Tables.Obiecte);
    }

    public int getId_obiect() {
        return id_obiect;
    }


    public String getNume_obiect() {
        return nume_obiect;
    }

    public void setNume_obiect(String nume_obiect) {
        this.nume_obiect = nume_obiect;
    }




    /**
     * Selecteaza toate obiectele din tabelul Obiecte
     * @return an array with all lessons
     * */
    public ArrayList<Obiect> getObiecte() {
        ArrayList<Obiect> obiecte = (ArrayList<Obiect>) db.selectAll(this);

        return obiecte;
    }
}
