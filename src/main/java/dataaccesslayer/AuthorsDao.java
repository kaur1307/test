/*
 * 
 * Author: Prabhsimran Kaur
 * Date: 24 July 2025
 * Description: Data Access Object interface for Authors
 */
package dataaccesslayer;

import java.sql.SQLException;
import java.util.List;
import transferobjects.AuthorDTO;

/**
 * Data Access Object interface for Author entities
 * Defines CRUD operations for Author data access
 * 
 * @author Prabhsimran Kaur
 * @version 1.0
 */
public interface AuthorsDao {
    
    /**
     * Get all authors from database
     * @return list of all authors
     * @throws SQLException if database error occurs
     */
    List<AuthorDTO> getAllAuthors() throws SQLException;
    
    /**
     * Get author by ID
     * @param authorID the author ID
     * @return the author or null if not found
     */
    AuthorDTO getAuthorByAuthorId(Integer authorID);
    
    /**
     * Add new author to database
     * @param author the author to add
     */
    void addAuthor(AuthorDTO author);
    
    /**
     * Update existing author
     * @param author the author to update
     */
    void updateAuthor(AuthorDTO author);
    
    /**
     * Delete author from database
     * @param author the author to delete
     */
    void deleteAuthor(AuthorDTO author);
}