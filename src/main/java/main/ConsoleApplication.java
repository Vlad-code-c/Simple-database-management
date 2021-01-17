package main;

import db.Tables;
import entity.Elev;
import entity.Grupa;
import entity.Obiect;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleApplication {
    public void start() {
        while (true) {
            showMenu();
            choiceOpt();
        }
    }

    public void showMenu() {
        System.out.println(setIndent(Color.ANSI_WHITE + Color.ANSI_RESET, "-"));
        System.out.println(setIndent(Color.ANSI_PURPLE + "Alegeti o optiune:" + Color.ANSI_RESET, " "));

        System.out.println(setIndent(Color.ANSI_WHITE + Color.ANSI_RESET, "-"));

        System.out.println(setIndent(Color.ANSI_GREEN + "1. Adaugati un elev " + Color.ANSI_RESET, " "));
        System.out.println(setIndent(Color.ANSI_GREEN + "2. Adaugati o grupa " + Color.ANSI_RESET, " "));
        System.out.println(setIndent(Color.ANSI_GREEN + "3. Adaugati un obiect " + Color.ANSI_RESET, " "));

        System.out.println(setIndent(Color.ANSI_WHITE + Color.ANSI_RESET, "-"));

        System.out.println(setIndent(Color.ANSI_CYAN + "11. Afiseaza toti elevii" + Color.ANSI_RESET, " "));
        System.out.println(setIndent(Color.ANSI_CYAN + "12. Afiseaza toate grupele" + Color.ANSI_RESET, " "));
        System.out.println(setIndent(Color.ANSI_CYAN + "13. Afiseaza toate obiectele" + Color.ANSI_RESET, " "));

        System.out.println(setIndent(Color.ANSI_WHITE + Color.ANSI_RESET, "-"));

        System.out.println(setIndent(Color.ANSI_RED + "21. Sterge un elev" + Color.ANSI_RESET, " "));
        System.out.println(setIndent(Color.ANSI_RED + "22. Sterge o clasa" + Color.ANSI_RESET, " "));
        System.out.println(setIndent(Color.ANSI_RED + "23. Sterge un obiect" + Color.ANSI_RESET, " "));

        System.out.println(setIndent(Color.ANSI_WHITE + Color.ANSI_RESET, "-"));
        System.out.println(setIndent(Color.ANSI_WHITE + "0. Iesire " + Color.ANSI_RESET, " "));
        System.out.println(setIndent(Color.ANSI_WHITE + Color.ANSI_RESET, "-"));

    }

    public void choiceOpt() {
        System.out.print("> ");
        switch (new Scanner(System.in).nextInt()) {
            case 1:
                readObject(Tables.Elev);
                break;
            case 2:
                readObject(Tables.Grupe);
                break;
            case 3:
                readObject(Tables.Obiecte);
                break;

            case 11:
                showObject(Tables.Elev);
                break;
            case 12:
                showObject(Tables.Grupe);
                break;
            case 13:
                showObject(Tables.Obiecte);
                break;

            case 21:
                deleteObject(Tables.Elev);
                break;
            case 22:
                deleteObject(Tables.Grupe);
                break;
            case 23:
                deleteObject(Tables.Obiecte);
                break;


            default:
                System.exit(0);
        }
    }


    /**
     * Sterge o inregistrare
     * */
    private void deleteObject(Tables tb) {
        ArrayList<?> objects = new ArrayList<Object>();

        if (tb.equals(Tables.Elev)) {
            objects = new Elev().getElevi();
        } else if (tb.equals(Tables.Grupe)) {
            objects = new Grupa().getGrupe();
        } else if (tb.equals(Tables.Obiecte)) {
            objects = new Obiect().getObiecte();
        }


        for (int i = 0; i < objects.size(); i++) {
            System.out.println(i + " - " + objects.get(i).toString());
        }



        while (true) {
            System.out.print("Introduceti Id-ul obiectului ce urmeaza a fi sters (-1 pentru a amana operatia): \n> ");
            int id = new Scanner(System.in).nextInt();

            if (id == -1) break;
            else if (id >= 0 && id < objects.size()) {
                if (tb.equals(Tables.Elev)) {
                    if (new Elev().deleteElev(((Elev) objects.get(id)).getIdnp())) {
                        System.out.println("Elevul a fost eliminat din baza de date");
                    } else {
                        System.out.println("Elevul nu a fost eliminat din baza de date");
                    }
                } else if (tb.equals(Tables.Grupe)) {
                    if (new Grupa().deleteGrupa(((Grupa) objects.get(id)).getNume_grupa())) {
                        System.out.println("Grupa nu a putut fi eliminata");
                    } else {
                        System.out.println("Grupa a fost eliminata");
                    }
                } else if (tb.equals(Tables.Obiecte)) {
                    if (new Obiect().deleteObiect(((Obiect) objects.get(id)).getId_obiect())) {
                        System.out.println("Obiectul a fost eliminat din baza de date");
                    } else {
                        System.out.println("Obiectul nu a fost eliminat din baza de date");
                    }
                }
                break;
            } else {
                System.out.println("Id-ul introdus este incorect. Incercati din nou!");
            }
        }
    }

    /**
     * Afiseaza toate inregistrarile dintr-un tabel corespunzator clasei necesare
     * */
    private void showObject(Tables tb) {
        System.out.print(Color.ANSI_CYAN);

        if (tb.equals(Tables.Elev)) {
            ArrayList<Elev> elevi = new Elev().getElevi();

            for (Elev elev : elevi) {
                System.out.println(elev.toString());
            }
        } else if (tb.equals(Tables.Obiecte)) {
            ArrayList<Obiect> obiecte = new Obiect().getObiecte();

            for (Obiect obiect : obiecte) {
                System.out.println(obiect.toString());
            }
        } else if (tb.equals(Tables.Grupe)) {
            ArrayList<Grupa> grupe = new Grupa().getGrupe();

            for (Grupa grupa : grupe) {
                System.out.println(grupa.toString());
            }
        }

        System.out.print(Color.ANSI_RESET);
    }

    /**
     * Citeste datele despre o inregistrare pentru clasa corespunzatoare
     * */
    private void readObject(Tables tb) {
        System.out.print(Color.ANSI_YELLOW);

        if (tb.equals(Tables.Elev)) {
            //1. Verify if exists Groups in bd
            if (new Grupa().getGrupe().size() <= 0) {
                System.out.println("Nu exista nici-o grupa in baza de date. Adaugati cel putin una pentru a putea introduce un elev.");
            }
            //2. Read data for student
            System.out.print("Introduceti idnp-ul elevului\n>");
            long idnp = new Scanner(System.in).nextLong();

            System.out.print("Introduceti numele elevului\n>");
            String nume = new Scanner(System.in).next();

            System.out.print("Introduceti prenumele elevului\n>");
            String prenume = new Scanner(System.in).next();

            Grupa grupa = getGroup();

            Elev elev = new Elev(idnp, nume, prenume, grupa.getNume_grupa());
            elev.addToDb();

        } else if (tb.equals(Tables.Grupe)) {
            System.out.print("Introduceti numele grupei: \n>");
            String group_name = new Scanner(System.in).next();

            //TODO: Verify if primary key is not violated
            Grupa grupa = new Grupa(group_name);
            grupa.addToDb();

        } else if (tb.equals(Tables.Obiecte)) {
            System.out.print("Introduceti numele obiectului: \n> ");
            Obiect obiect = new Obiect(new Scanner(System.in).nextLine());
            obiect.addToDb();

        }

        System.out.print(Color.ANSI_RESET);
    }


    /**
     * Permite utilizatorului sa aleaga dintre grupele aflate in bd
     * */
    private Grupa getGroup() {
        //TODO: Verify if grupe is not empty

        boolean isValidId = false;

        System.out.print("Introduceti id-ul grupei dorite: \n");

        Grupa grupa = new Grupa();
        ArrayList<Grupa> grupe = grupa.getGrupe();

        for (int i = 1; i < grupe.size() + 1; i++) {
            System.out.println(i + " - " + grupe.get(i - 1).getNume_grupa());
        }

        while (!isValidId) {

            System.out.print("> ");
            int id = new Scanner(System.in).nextInt() - 1;

            if (id >= 0 || id < grupe.size()) {
                return grupe.get(id);
            } else {
                System.out.println("Id-ul introdus este indisponibil. Incercati din nou");
            }
        }

        return null;
    }

    /**
     * Aranjeaza textul la mijloc
     * */
    private String setIndent(String str, String symb) {
        int indent = 60;

        symb = symb.equals("") ? " " : symb;

        while (str.length() < indent) {
            str = symb + str + symb;
        }

        return "|" + str + "|";
    }
}
