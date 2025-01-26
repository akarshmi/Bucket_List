package BucketList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Static variable to hold the connection
    private static Connection databaseConnection;

    // Static block to load the driver and establish the connection
    static {
        String DATABASE_URL = "jdbc:mysql://localhost:3306/buckylog";
        String USERNAME = "root";
        String PASSWORD = "0325";

        try {
            // Step 1: Load and register the driver (JDBC 4.0 and above should load automatically)
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.err.println("Driver Loaded Successfully.");

            // Step 2: Establish the connection
            databaseConnection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            System.err.println("Connection Established Successfully.");

        } catch (ClassNotFoundException e) {
            System.err.println("Driver not found. Please check if you have the MySQL JDBC driver.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error establishing connection to the database.");
            e.printStackTrace();
        }
    }

    // Method to return the connection
    public static Connection getConnection() {
        return databaseConnection;
    }

    // Method to close the connection when done
    public static void closeConnection() {
        try {
            if (databaseConnection != null && !databaseConnection.isClosed()) {
                databaseConnection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing the database connection.");
            e.printStackTrace();
        }
    }


}
