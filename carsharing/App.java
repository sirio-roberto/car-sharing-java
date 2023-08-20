package carsharing;

import carsharing.db.Database;

public class App {
    private final Database db;


    public App(String dbName) {
        this.db = new Database(dbName);
    }

    public void run() {
        db.createCompanyTable();
    }
}
