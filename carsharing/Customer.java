package carsharing;

public class Customer {
    private int id;
    private final String name;
    private Car car;

    public Customer(int id, String name, Car car) {
        this.id = id;
        this.name = name;
        this.car = car;
    }

    public Customer(String name, Car car) {
        this.name = name;
        this.car = car;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
