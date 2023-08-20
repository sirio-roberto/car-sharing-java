package carsharing;

import carsharing.db.Database;

import java.util.List;
import java.util.Scanner;

public class App {
    private final Scanner scan = new Scanner(System.in);
    private final Database db;

    public App(String dbName) {
        this.db = new Database(dbName);
    }

    public void run() {
        db.createCompanyTable();
    }



    static abstract class Command {
        abstract void execute();
    }

    private class ManagerLoginCommand extends Command {

        @Override
        void execute() {
            System.out.println("""
                    
                    1. Company list
                    2. Create a company
                    0. Back""");

            String userAction = scan.nextLine();
            System.out.println();

            switch (userAction) {
                case "1" -> new listCompaniesCommand().execute();
            }
        }
    }

    private class listCompaniesCommand extends Command {

        @Override
        void execute() {
            List<Company> companies = db.getAllCompanies();
            if (companies.isEmpty()) {
                System.out.println("The company list is empty!");
            } else {
                companies.forEach(System.out::println);
            }
        }
    }
}
