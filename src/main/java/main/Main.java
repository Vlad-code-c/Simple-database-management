package main;

import db.DatabaseHandler;

public class Main {
    public static DatabaseHandler db = new DatabaseHandler();

    public static void main(String[] args) {
        new ConsoleApplication().start();
    }
}
