/*
 * 
 * Author: Prabhsimran Kaur
 * Date: 24 July 2025
 * Description: Custom exception for validation errors
 */
package businesslayer;

/**
 * Custom exception class for handling validation errors
 * Used throughout the business layer for data validation
 * 
 * @author Prabhsimran Kaur
 * 
 */
public class ValidationException extends Exception {
    
    /**
     * Default constructor with standard message
     */
    public ValidationException(){
        super("Data not in valid format");
    }
    
    /**
     * Constructor with custom message
     * @param message the error message
     */
    public ValidationException(String message){
        super(message);
    }
    
    /**
     * Constructor with message and cause
     * @param message the error message
     * @param throwable the underlying cause
     */
    public ValidationException(String message, Throwable throwable){
        super(message, throwable);
    }
    
    /**
     * Constructor with cause only
     * @param throwable the underlying cause
     */
    public ValidationException(Throwable throwable){
        super(throwable);
    }
}