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

        String userAction;
        do {
            System.out.println("""
                1. Log in as a manager
                0. Exit""");

            userAction = scan.nextLine();
            if ("1".equals(userAction)) {
                new ManagerLoginCommand().execute();
            }
        } while (!"0".equals(userAction));
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
                case "1" -> new ListCompaniesCommand().execute();
                case "2" -> new CreateCompanyCommand().execute();
            }

            if (!"0".equals(userAction)) {
                execute();
            }
        }
    }

    private class ListCompaniesCommand extends Command {

        @Override
        void execute() {
            List<Company> companies = db.getAllCompanies();
            if (companies.isEmpty()) {
                System.out.println("The company list is empty!");
            } else {
                System.out.println("Company list:");
                companies.forEach(System.out::println);
            }
        }
    }

    private class CreateCompanyCommand extends Command {

        @Override
        void execute() {
            System.out.println("Enter the company name:");
            String name = scan.nextLine();
            Company company = new Company(name);

            db.insertCompany(company);
            System.out.println("The company was created!");
        }
    }
}
