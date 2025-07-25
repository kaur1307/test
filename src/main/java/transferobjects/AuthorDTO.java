/*
 * File: AuthorDTO.java
 * Author: Thuvarahan Ragunathan
 * Date: 2025
 * Description: Data Transfer Object for Author entities from the Authors table
 */
package transferobjects;

/**
 * Data Transfer Object for Author entities
 * Represents an author record from the Authors table in the books database
 * 
 * @author Thuvarahan Ragunathan
 * @version 1.0
 */
public class AuthorDTO {
    private Integer authorID;
    private String firstName;
    private String lastName;
    
    /**
     * Gets the author ID
     * @return the author ID
     */
    public Integer getAuthorID() {
        return authorID;
    }
    
    /**
     * Sets the author ID
     * @param authorID the author ID to set
     */
    public void setAuthorID(Integer authorID) {
        this.authorID = authorID;
    }
    
    /**
     * Gets the first name
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Sets the first name
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * Gets the last name
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Sets the last name
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}