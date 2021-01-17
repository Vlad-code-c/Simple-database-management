package entity;

import db.DatabaseHandler;
import db.Tables;
import main.Main;

import java.util.ArrayList;

public class Elev {
    private long idnp;
    private String nume;
    private String prenume;
    private String nume_grupa;


    private static Tables tb = Tables.Elev;
    private static String primaryKeyColumnName = "idnp_elev";

    private DatabaseHandler db;


    public Elev(long idnp, String nume, String prenume, String nume_grupa) {
        this.idnp = idnp;
        this.nume = nume;
        this.prenume = prenume;
        this.nume_grupa = nume_grupa;

        db = Main.db;
    }

    public Elev() {
        db = Main.db;
    }

    public void addToDb() {
        if (db.insertValue(this)) {
            System.out.println("Elevul a fost adaugat cu succes");
        } else {
            System.out.println("Elevul nu a fost adaugat!");
        }
    }


    public long getIdnp() {
        return idnp;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getNume_grupa() {
        return nume_grupa;
    }

    public void setNume_grupa(String nume_grupa) {
        this.nume_grupa = nume_grupa;
    }

    /**
     * Selecteaza toti elevii din tabelul Elevi
     * @return an array with all students
     * */
    public ArrayList<Elev> getElevi() {
        ArrayList<Elev> elevi = (ArrayList<Elev>) db.selectAll(this);

        return elevi;
    }

    public boolean deleteElev(long idnp) {
        return db.deleteValue(Tables.Elev, "idnp_elev", String.valueOf(idnp));
    }

    @Override
    public String toString() {
        return "IDNP: " + idnp +
                ", nume: " + nume +
                ", prenume: " + prenume +
                ", nume grupa: " + nume_grupa;
    }
}
