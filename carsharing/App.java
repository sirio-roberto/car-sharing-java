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
        String userAction;
        do {
            System.out.println("""
                    1. Log in as a manager
                    2. Log in as a customer
                    3. Create a customer
                    0. Exit""");

            userAction = scan.nextLine();
            System.out.println();
            switch (userAction) {
                case "1" -> new ManagerLoginCommand().execute();
                case "3" -> new CreateCustomerCommand().execute();
            }
            System.out.println();

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

    private class ManageCarsCommand extends Command {
        private final Company company;

        public ManageCarsCommand(Company company) {
            this.company = company;
        }

        @Override
        void execute() {
            System.out.printf("""
                    
                    '%s' company
                    1. Car list
                    2. Create a car
                    0. Back
                    """, company.getName());

            String userAction = scan.nextLine();
            System.out.println();

            switch (userAction) {
                case "1" -> new ListCarsCommand(company).execute();
                case "2" -> new CreateCarCommand(company).execute();
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
                System.out.println("Choose the company:");
                for (int i = 0; i < companies.size(); i++) {
                    System.out.printf("%s. %s\n", i + 1, companies.get(i).getName());
                }
                System.out.println("0. Back");

                String userAction = scan.nextLine();
                if (!"0".equals(userAction)) {
                    new ManageCarsCommand(companies.get(Integer.parseInt(userAction) - 1)).execute();
                }
            }
        }
    }

    private class ListCarsCommand extends Command {
        private final Company company;

        public ListCarsCommand(Company company) {
            this.company = company;
        }
        @Override
        void execute() {
            List<Car> cars = db.getCarsByCompany(company);
            if (cars.isEmpty()) {
                System.out.println("The car list is empty!");
            } else {
                System.out.println("Car list:");
                for (int i = 0; i < cars.size(); i++) {
                    System.out.printf("%s. %s\n", i + 1, cars.get(i).getName());
                }
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

    private class CreateCarCommand extends Command {
        private final Company company;

        public CreateCarCommand(Company company) {
            this.company = company;
        }

        @Override
        void execute() {
            System.out.println("Enter the car name:");
            String name = scan.nextLine();
            Car car = new Car(name, company);

            db.insertCar(car);
            System.out.println("The car was created!");
        }
    }

    private class CreateCustomerCommand extends Command {

        @Override
        void execute() {
            System.out.println("Enter the customer name:");
            String name = scan.nextLine();
            Customer customer = new Customer(name, null);

            db.insertCustomer(customer);
            System.out.println("The customer was created!");
        }
    }
}
