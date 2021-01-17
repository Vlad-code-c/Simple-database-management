package db;

import entity.Elev;
import entity.Grupa;
import entity.Obiect;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler {
    private String db_connection_url = "";
    private SQLiteDataSource dataSource = new SQLiteDataSource();
    private String databaseName = "Colegiu";

    private static String fileUrlGrupe = "src\\main\\java\\db\\tables\\Grupe.sql";
    private static String fileUrlElev = "src\\main\\java\\db\\tables\\Elev.sql";
    private static String fileUrlObiecte = "src\\main\\java\\db\\tables\\Obiecte.sql";

    public DatabaseHandler() {
        this.db_connection_url = "jdbc:sqlite:" + databaseName;
        dataSource.setUrl(this.db_connection_url);

        createDatabase();
    }


    /**
     * Creez baza de date, daca aceasta nu exista
     * */
    private void createDatabase() {
        try {
            Connection con = getConnection();
            con.createStatement().execute(FileReader.getSqlScript(fileUrlGrupe));
            con.createStatement().execute(FileReader.getSqlScript(fileUrlElev));
            con.createStatement().execute(FileReader.getSqlScript(fileUrlObiecte));

            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Primeste conectiunea, care ulterior va fi folosita pentru a comunica cu baza de date.
     * @return a connection to the data source
     * */
    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();

            if (!connection.isValid(5)) {
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return connection;
    }

    /**
     * Adauga o inregistrare noua in baza de date, in tabelul corespunzator clasei
     * @param obj object which will inserted in db
     * @return true if succes, false if not
     * */
    public boolean insertValue(Object obj) {
        Connection con = getConnection();
        PreparedStatement prSt = null;

        try {
            if (obj instanceof Elev) {
                String insert = "INSERT INTO ELEV(idnp_elev, nume, prenume, id_grupa) VALUES (?, ?, ?, ?);";
                prSt = con.prepareStatement(insert);

                prSt.setLong(1, ((Elev) obj).getIdnp());
                prSt.setString(2, ((Elev) obj).getNume());
                prSt.setString(3, ((Elev) obj).getPrenume());
                prSt.setString(4, ((Elev) obj).getNume_grupa());

            } else if (obj instanceof Grupa) {
                String insert = "INSERT INTO Grupe(nume_grupa) VALUES (?);";
                prSt = con.prepareStatement(insert);

                prSt.setString(1, ((Grupa) obj).getNume_grupa());

            } else if (obj instanceof Obiect) {
                String insert = "INSERT INTO Obiecte(id_obiect, nume_obiect) VALUES (?, ?);";
                prSt = con.prepareStatement(insert);

                prSt.setLong(1, ((Obiect) obj).getId_obiect());
                prSt.setString(2, ((Obiect) obj).getNume_obiect());
            }


            if (prSt != null) {
                prSt.execute();

            }

            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

        return true;
    }


    /**
     * Actualizeaza o valoare dintr-un tabel, folosind variabilele primite ca parametru
     * @param tb tabelul in care urmeaza a fi actualizate datele
     * @param columnName coloana in care urmeaza a fi actualizate datele
     * @param newValue noua valoare
     * @param primaryKeyColumnName numele coloanei dupa care se va face referinta (cheia primara)
     * @param primaryKeyReferenceValue valoarea coloanei dupa care se va face referinta
     * @return true if succes, false in other case
     * */
    public boolean updateValue(Tables tb, String columnName, String newValue, String primaryKeyColumnName, String primaryKeyReferenceValue) {
        Connection con = getConnection();

        try {
            String update = "UPDATE ? SET ? = ? WHERE ? LIKE ?";
            PreparedStatement prst = con.prepareStatement(update);

            prst.setString(1, tb.name());
            prst.setString(2, columnName);
            prst.setString(3, newValue);
            prst.setString(4, primaryKeyColumnName);
            prst.setString(5, primaryKeyReferenceValue);

            prst.executeUpdate();
            con.close();

            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }



    /**
     * Selecteaza o singura valoare pentru coloana si valoare cheii primare indicate, din tabel
     * @param tb tabelul din care urmeaza a fi citite datele
     * @param columnName coloana din care urmeaza a fi citite datele
     * @param primaryKeyColumnName numele coloanei dupa care se va face referinta (cheia primara)
     * @param primaryKeyReferenceValue valoarea coloanei dupa care se va face referinta
     * @return object that reprezent value from db, or null
     * */
    public Object selectValue(Tables tb, String columnName, String primaryKeyColumnName, String primaryKeyReferenceValue) {
        Connection con = getConnection();
        Object value = null;

        try {
            String select = "SELECT ? FROM ? WHERE ? LIKE ?";
            PreparedStatement prSt = con.prepareStatement(select);

            prSt.setString(1, columnName);
            prSt.setString(2, tb.name());
            prSt.setString(3, primaryKeyColumnName);
            prSt.setString(4, primaryKeyReferenceValue);

            ResultSet resSet = prSt.executeQuery();

            if (resSet.next()) {
                value = resSet.getObject(1);
            }
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return value;
    }


    /**
     * Selecteaza toate inregistrarile din tabelul corespunzator clasei obiectului necesar
     * */
    public ArrayList<?> selectAll(Object obj) {
        Connection con = getConnection();
        PreparedStatement prSt = null;
        ResultSet resSet = null;
        String select = "";

        try {
            if (obj instanceof Elev) {
                select = "SELECT * FROM Elev;";
                prSt = con.prepareStatement(select);
                resSet = prSt.executeQuery();

                ArrayList<Elev> elevi = new ArrayList<Elev>();

                while (resSet.next()) {
                    Elev elev = new Elev(resSet.getLong(1),
                            resSet.getString(2),
                            resSet.getString(3),
                            resSet.getString(4));

                    elevi.add(elev);
                }

                con.close();
                return elevi;

            } else if (obj instanceof Grupa) {
                select = "SELECT * FROM Grupe;";
                prSt = con.prepareStatement(select);
                resSet = prSt.executeQuery();

                ArrayList<Grupa> grupe = new ArrayList<Grupa>();

                while (resSet.next()) {
                    Grupa grupa = new Grupa(resSet.getString(1));
                    grupe.add(grupa);
                }

                con.close();
                return grupe;

            } else if (obj instanceof Obiect) {
                select = "SELECT * FROM Obiecte;";
                prSt = con.prepareStatement(select);
                resSet = prSt.executeQuery();

                ArrayList<Obiect> obiecte = new ArrayList<Obiect>();

                while (resSet.next()) {
                    Obiect obiect = new Obiect(resSet.getInt(1),
                            resSet.getString(2));
                    obiecte.add(obiect);
                }

                con.close();
                return obiecte;

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    /**
     * Returneaza ultimul id din baza de date
     * */
    public int getLastId(Tables tb) {
        Connection con = getConnection();
        String select = "SELECT MAX(id_obiect) FROM Obiecte;";
        int lastId = 0;

        try {
            if (tb.equals(Tables.Obiecte)) {
                Statement st = con.createStatement();
                ResultSet res = st.executeQuery(select);

                if (res.next()) {
                    lastId = res.getInt(1);
                }

                con.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return lastId;
    }
}
