package carsharing;

public class Car {
    private int id;
    private final String name;
    private final Company company;

    public Car(int id, String name, Company company) {
        this.id = id;
        this.name = name;
        this.company = company;
    }

    public Car(String name, Company company) {
        this.name = name;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Company getCompany() {
        return company;
    }
}
