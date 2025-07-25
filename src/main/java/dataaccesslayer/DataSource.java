/*
 * File: DataSource.java
 * Author: Thuvarahan Ragunathan
 * Date: 2025
 * Description: Singleton DataSource for database connections
 */
package dataaccesslayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import transferobjects.CredentialsDTO;

/**
 * Singleton DataSource class for managing database connections
 * Implements Singleton design pattern as required by the assignment
 * 
 * @author Thuvarahan Ragunathan
 * @version 1.0
 */
public class DataSource {
    private static DataSource instance;
    private Connection connection = null;
    private String url = "jdbc:mysql://localhost:3306/books?useSSL=false&allowPublicKeyRetrieval=true";
    private String username;
    private String password;

    /**
     * Private constructor to prevent direct instantiation
     */
    private DataSource() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get singleton instance
     * @return DataSource instance
     */
    public static synchronized DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    /**
     * Set credentials for database connection
     * @param creds database credentials
     */
    public void setCredentials(CredentialsDTO creds) {
        this.username = creds.getUsername();
        this.password = creds.getPassword();
    }

    /**
     * Create database connection
     * @return database connection
     * @throws SQLException if connection fails
     */
    public Connection getConnection() throws SQLException {
        if (username == null || password == null) {
            throw new SQLException("Database credentials not set. Call setCredentials() first.");
        }
        
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            } else {
                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return connection;
    }
}