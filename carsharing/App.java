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
                case "2" -> new ListCustomersCommand().execute();
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

    private class CustomerLoginCommand extends Command {
        private final Customer customer;

        public CustomerLoginCommand(Customer customer) {
            this.customer = customer;
        }

        @Override
        void execute() {
            System.out.println("""
                    
                    1. Rent a car
                    2. Return a rented car
                    3. My rented car
                    0. Back""");

            String userAction = scan.nextLine();
            System.out.println();

            switch (userAction) {
                case "1" -> new RentCarCommand(customer).execute();
                case "2" -> new ReturnCarCommand(customer).execute();
                case "3" -> new ShowRentedCarCommand(customer).execute();
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

    private class ListCustomersCommand extends Command {

        @Override
        void execute() {
            List<Customer> customers = db.getAllCustomers();
            if (customers.isEmpty()) {
                System.out.println("The customer list is empty!");
            } else {
                System.out.println("Customer list:");
                for (int i = 0; i < customers.size(); i++) {
                    System.out.printf("%s. %s\n", i + 1, customers.get(i).getName());
                }
                System.out.println("0. Back");

                String userAction = scan.nextLine();
                if (!"0".equals(userAction)) {
                    new CustomerLoginCommand(customers.get(Integer.parseInt(userAction) - 1)).execute();
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

    private class RentCarCommand extends Command {
        private final Customer customer;

        private RentCarCommand(Customer customer) {
            this.customer = customer;
        }

        @Override
        void execute() {
            List<Company> companies = db.getAllCompanies();
            if (companies.isEmpty()) {
                System.out.println("The company list is empty!");
            } else if (customer.getCar() != null) {
                System.out.println("You've already rented a car!");
            } else {
                System.out.println("Choose the company:");
                for (int i = 0; i < companies.size(); i++) {
                    System.out.printf("%s. %s\n", i + 1, companies.get(i).getName());
                }
                System.out.println("0. Back");

                String userAction = scan.nextLine();
                if (!"0".equals(userAction)) {
                    System.out.println();

                    Company selectedCompany = companies.get(Integer.parseInt(userAction) - 1);
                    List<Car> companyCars = db.getCarsByCompany(selectedCompany, false);

                    if (companyCars.isEmpty()) {
                        System.out.printf("No available cars in the '%s'\n", selectedCompany.getName());
                    } else {
                        System.out.println("Choose a car:");
                        for (int i = 0; i < companyCars.size(); i++) {
                            System.out.printf("%s. %s\n", i + 1, companyCars.get(i).getName());
                        }

                        String userChoice = scan.nextLine();
                        try {
                            Car chosenCar = companyCars.get(Integer.parseInt(userChoice) - 1);

                            db.updateCarRental(customer, chosenCar, true);
                            customer.setCar(chosenCar);
                            System.out.printf("You rented '%s'\n", chosenCar.getName());
                        } catch (IndexOutOfBoundsException ex) {
                            System.out.println("Invalid car number!");
                        }
                    }
                }
            }
        }
    }

    private class ReturnCarCommand extends Command {
        private final Customer customer;

        private ReturnCarCommand(Customer customer) {
            this.customer = customer;
        }

        @Override
        void execute() {
            if (customer.getCar() == null) {
                System.out.println("You didn't rent a car!");
            } else {
                db.updateCarRental(customer, customer.getCar(), false);
                customer.setCar(null);
                System.out.println("You've returned a rented car!");
            }
        }
    }

    private static class ShowRentedCarCommand extends Command {
        private final Customer customer;

        private ShowRentedCarCommand(Customer customer) {
            this.customer = customer;
        }

        @Override
        void execute() {
            if (customer.getCar() == null) {
                System.out.println("You didn't rent a car!");
            } else {
                System.out.println("Your rented car:");
                System.out.println(customer.getCar().getName());
                System.out.println("Company:");
                System.out.println(customer.getCar().getCompany().getName());
            }
        }
    }
}
