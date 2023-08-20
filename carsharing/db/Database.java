package carsharing.db;

import java.sql.*;

public class Database {
    private final String CREATE_COMPANY_TB = """
            CREATE TABLE COMPANY (
            id INT PRIMARY KEY,
            name VARCHAR(255)
            );""";
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
