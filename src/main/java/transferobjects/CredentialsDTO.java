/*
 * File: CredentialsDTO.java
 * Author: Prabhsimran Kaur (041119310)
 * Date: 24 July 2025
 * Description: DTO for storing and transferring DBMS login credentials
 */
package transferobjects;

/**
 * A DTO used to store and transfer DBMS login credentials
 * between businesslayer and dataaccesslayer
 * 
 * @author Prabhsimran Kaur (041119310)
 * 
 */
public class CredentialsDTO {
    private String username;
    private String password;

    /**
     * Simple getter for user name portion of the credentials
     * @return user name portion of the credentials
     */
    public String getUsername() {
        return username;
    }

    /**
     * Simple setter for user name portion of the credentials
     * @param username user name portion of the credentials
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Simple getter for password portion of the credentials
     * @return password portion of the credentials
     */
    public String getPassword() {
        return password;
    }

    /**
     * Simple setter for password portion of the credentials
     * @param password password portion of the credentials
     */
    public void setPassword(String password) {
        this.password = password;
    }
}