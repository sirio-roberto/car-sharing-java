package carsharing.db;

import carsharing.Car;
import carsharing.Company;
import carsharing.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final String CREATE_COMPANY_TB = """
            CREATE TABLE IF NOT EXISTS COMPANY (
            id INT PRIMARY KEY AUTO_INCREMENT,
            name VARCHAR(255) UNIQUE NOT NULL
            );""";

    private final String CREATE_CAR_TB = """
            CREATE TABLE IF NOT EXISTS CAR (
            id INT PRIMARY KEY AUTO_INCREMENT,
            name VARCHAR(255) UNIQUE NOT NULL,
            company_id INT NOT NULL,
            CONSTRAINT fk_company FOREIGN KEY (company_id)
            REFERENCES COMPANY(id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
            );""";

    private final String CREATE_CUSTOMER_TB = """
            CREATE TABLE IF NOT EXISTS CUSTOMER (
            id INT PRIMARY KEY AUTO_INCREMENT,
            name VARCHAR(255) UNIQUE NOT NULL,
            rent_car_id INT,
            CONSTRAINT fk_car FOREIGN KEY (rent_car_id)
            REFERENCES CAR(id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
            );""";

    private final String INSERT_COMPANY = "INSERT INTO COMPANY (name) VALUES (?);";
    private final String INSERT_CAR = "INSERT INTO CAR (name, company_id) VALUES (?, ?);";
    private final String INSERT_CUSTOMER = "INSERT INTO CUSTOMER (name) VALUES (?);";
    private final String SELECT_ALL_COMPANIES = "SELECT * FROM COMPANY;";
    private final String SELECT_ALL_CUSTOMERS = "SELECT * FROM CUSTOMER;";
    private final String SELECT_CARS_BY_COMPANY = "SELECT * FROM CAR WHERE company_id = ?;";
    private String URL = "jdbc:h2:./src/carsharing/db/";
    private Connection conn;

    public Database(String dbName) {
        URL += dbName;

        createCompanyTable();
        createCarTable();
        createCustomerTable();
    }

    private Connection getConnection() throws SQLException {
        if (conn != null) {
            return conn;
        }
        conn = DriverManager.getConnection(URL);
        conn.setAutoCommit(true);
        return conn;
    }

    public void createCompanyTable() {
        createTable(CREATE_COMPANY_TB);
    }

    public void createCarTable() {
        createTable(CREATE_CAR_TB);
    }

    private void createCustomerTable() {
        createTable(CREATE_CUSTOMER_TB);
    }

    private void createTable(String createTableQuery) {
        Statement st = null;
        try {
            st = getConnection().createStatement();
            st.executeUpdate(createTableQuery);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            closeStatement(st);
        }
    }

    public void insertCompany(Company company) {
        PreparedStatement st = null;
        try {
            st = getConnection().prepareStatement(INSERT_COMPANY);
            st.setString(1, company.getName());
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            closeStatement(st);
        }
    }

    public void insertCar(Car car) {
        PreparedStatement st = null;
        try {
            st = getConnection().prepareStatement(INSERT_CAR);
            st.setString(1, car.getName());
            st.setInt(2, car.getCompany().getId());
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            closeStatement(st);
        }
    }

    public void insertCustomer(Customer customer) {
        PreparedStatement st = null;
        try {
            st = getConnection().prepareStatement(INSERT_CUSTOMER);
            st.setString(1, customer.getName());
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            closeStatement(st);
        }
    }

    public List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = getConnection().createStatement();
            rs = st.executeQuery(SELECT_ALL_COMPANIES);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                companies.add(new Company(id, name));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            closeResultSet(rs);
            closeStatement(st);
        }
        return companies;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = getConnection().createStatement();
            rs = st.executeQuery(SELECT_ALL_CUSTOMERS);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                customers.add(new Customer(id, name, null));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            closeResultSet(rs);
            closeStatement(st);
        }
        return customers;
    }

    public List<Car> getCarsByCompany(Company company) {
        List<Car> cars = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = getConnection().prepareStatement(SELECT_CARS_BY_COMPANY);
            st.setInt(1, company.getId());

            rs = st.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                cars.add(new Car(id, name, company));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            closeResultSet(rs);
            closeStatement(st);
        }
        return cars;
    }

    private static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
