/*
 * 
 * Author: Prabhsimran Kaur
 * Date: 24 July 2025
 * Description: Business logic layer for Authors with validation
 */
package businesslayer;

import java.util.List;
import dataaccesslayer.AuthorsDao;
import dataaccesslayer.AuthorsDaoImpl;
import java.sql.SQLException;
import transferobjects.AuthorDTO;
import transferobjects.CredentialsDTO;

/**
 * Business logic layer for Authors operations
 * Handles validation and business rules for Author entities
 * 
 * @author Prabhsimran Kaur
 * Reference taken from Stanley Pieda Web application.
 */
public class AuthorsBusinessLogic {
    private static final int FIRST_NAME_MAX_LENGTH = 30;
    private static final int LAST_NAME_MAX_LENGTH = 30;
    
    private AuthorsDao authorsDao = null;
    private CredentialsDTO creds;
    
    /**
     * Constructor with credentials
     * @param creds database credentials
     */
    public AuthorsBusinessLogic(CredentialsDTO creds){
        this.creds = creds;
        authorsDao = new AuthorsDaoImpl(creds);
    }
    
    /**
     * Get all authors from database
     * @return list of all authors
     * @throws SQLException if database error occurs
     */
    public List<AuthorDTO> getAllAuthors() throws SQLException {
        return authorsDao.getAllAuthors();
    }
    
    /**
     * Get author by ID
     * @param authorID the author ID
     * @return the author or null if not found
     */
    public AuthorDTO getAuthor(Integer authorID){
        return authorsDao.getAuthorByAuthorId(authorID);
    }
    
    /**
     * Add new author with validation
     * @param author the author to add
     * @throws ValidationException if validation fails
     */
    public void addAuthor(AuthorDTO author) throws ValidationException{
        cleanAuthor(author);
        validateAuthor(author);
        authorsDao.addAuthor(author);
    }
    
    /**
     * Update author with validation
     * @param author the author to update
     * @throws ValidationException if validation fails
     */
    public void updateAuthor(AuthorDTO author) throws ValidationException{
        cleanAuthor(author);
        validateAuthor(author);
        authorsDao.updateAuthor(author);
    }
    
    /**
     * Delete author
     * @param author the author to delete
     */
    public void deleteAuthor(AuthorDTO author){
        authorsDao.deleteAuthor(author);
    }
    
    /**
     * Clean whitespace from author fields
     * @param author the author to clean
     */
    private void cleanAuthor(AuthorDTO author){
        if(author.getFirstName() != null){ 
            author.setFirstName(author.getFirstName().trim());
        }
        if(author.getLastName() != null){ 
            author.setLastName(author.getLastName().trim());
        }
    }
    
    /**
     * Validate author data
     * @param author the author to validate
     * @throws ValidationException if validation fails
     */
    private void validateAuthor(AuthorDTO author) throws ValidationException{
        validateString(author.getFirstName(), "First Name", FIRST_NAME_MAX_LENGTH, false);
        validateString(author.getLastName(), "Last Name", LAST_NAME_MAX_LENGTH, false);
    }
    
    /**
     * Validate string field
     * @param value the value to validate
     * @param fieldName the field name for error messages
     * @param maxLength maximum allowed length
     * @param isNullAllowed whether null is allowed
     * @throws ValidationException if validation fails
     */
    private void validateString(String value, String fieldName, int maxLength, boolean isNullAllowed)
        throws ValidationException{
        if(value == null && isNullAllowed){
            return; // null permitted, nothing to validate
        }
        else if(value == null && !isNullAllowed){
            throw new ValidationException(String.format("%s cannot be null", fieldName));
        }
        else if(value.length() == 0){
            throw new ValidationException(String.format("%s cannot be empty or only whitespace", fieldName));
        }
        else if(value.length() > maxLength){
            throw new ValidationException(String.format("%s cannot exceed %d characters", fieldName, maxLength));
        }
    }
}