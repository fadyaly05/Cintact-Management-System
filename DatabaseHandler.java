package main;

import java.sql.*;
import java.util.*;

public class DatabaseHandler {

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3309/database"; // Update with your DB URL
    private static final String USER = "root"; // Update with your DB username
    private static final String PASSWORD = ""; // Update with your DB password

    // Connection setup
    private Connection connection;

    public DatabaseHandler() {
        try {
            // Register MySQL JDBC driver explicitly
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully.");

            // Try connecting to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database.");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Save contacts to the database
    public void saveDataBase(List<Contact> contacts) {
        String query = "INSERT INTO contacts (name, phone_number, address, email) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (Contact contact : contacts) {
                stmt.setString(1, contact.getName());
                stmt.setString(2, contact.getPhoneNumber());
                stmt.setString(3, contact.getAddress());
                stmt.setString(4, contact.getEmail());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read contacts from the database
    public List<Contact> readDataBase() {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts";

        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone_number"); // Ensure this matches your DB column
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                contacts.add(new Contact(name, phone, address, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;
    }

    // Update contact in the database
    public void updateDatabase(List<Contact> contacts) {
        String query = "UPDATE contacts SET name = ?, phone_number = ?, address = ?, email = ? WHERE name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (Contact contact : contacts) {
                stmt.setString(1, contact.getName());
                stmt.setString(2, contact.getPhoneNumber());
                stmt.setString(3, contact.getAddress());
                stmt.setString(4, contact.getEmail());
                stmt.setString(5, contact.getName()); // Assuming you're updating by name
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete contact from the database
    public void deleteContact(String name) {
        String query = "DELETE FROM contacts WHERE name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Close the database connection
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}