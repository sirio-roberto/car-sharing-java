package carsharing.db;

import carsharing.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final String CREATE_COMPANY_TB = """
            CREATE TABLE COMPANY (
            id INT PRIMARY KEY,
            name VARCHAR(255) UNIQUE NOT NULL
            );""";

    private final String SELECT_ALL_COMPANIES = "SELECT * FROM COMPANY";
    private String URL = "jdbc:h2:./src/carsharing/db/";
    private Connection conn;

    public Database(String dbName) {
        URL += dbName;
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
        Statement st = null;
        try {
            st = getConnection().createStatement();
            st.executeUpdate(CREATE_COMPANY_TB);
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
