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
        System.out.println(setIndent("Alegeti o optiune:", " "));
        System.out.println(setIndent("1. Adaugati un elev", "-"));
        System.out.println(setIndent("2. Adaugati o grupa", "-"));
        System.out.println(setIndent("3. Adaugati un obiect", "-"));
        System.out.println(setIndent("0. Iesire", "-"));
    }

    public void choiceOpt() {
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


            default:
                System.exit(0);
        }
    }



    private Object readObject(Tables tb) {
        if (tb.equals(Tables.Elev)) {
            //1. Verify if exists Groups in bd
            if (new Grupa().getGrupe().size() <= 0) {
                System.out.println("Nu exista nici-o grupa in baza de date. Adaugati cel putin una pentru a putea introduce un elev.");
                return null;
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

        return null;
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
     * Aranjeaza
     * */
    private String setIndent(String str, String symb) {
        int indent = 80;

        symb = !symb.equals("") ? " " : symb;

        while (str.length() < 80) {
            str = symb + str + symb;
        }

        return str;
    }
}
