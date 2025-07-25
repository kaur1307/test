/*
 * File: AddAuthorServlet.java
 * Author: Thuvarahan Ragunathan
 * Date: 2025
 * Description: Servlet to add new author
 */
package viewlayer;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import businesslayer.AuthorsBusinessLogic;
import businesslayer.ValidationException;
import transferobjects.AuthorDTO;
import transferobjects.CredentialsDTO;

/**
 * Servlet to add new author
 * Handles form display and author creation with validation
 * 
 * @author Prabhsimran Kaur
 * @version 1.0
 */
public class AddAuthorServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Add New Author</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
            out.println("table { border-collapse: collapse; margin: 20px 0; }");
            out.println("td { padding: 8px; text-align: left; }");
            out.println("form { margin: 20px 0; }");
            out.println("input[type='text'] { padding: 5px; margin: 5px; width: 200px; }");
            out.println("input[type='submit'] { padding: 8px 16px; margin: 5px; }");
            out.println(".success { color: green; font-weight: bold; }");
            out.println(".error { color: red; font-weight: bold; }");
            out.println(".footer-info { margin-top: 40px; font-size: 14px; color: #333; font-family: 'Times New Roman', serif; text-align: left; display: inline-block; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Add New Author</h1>");
            
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            
            if (firstName == null || lastName == null) {
                // Show add form
                out.println("<form method='post' action='addAuthor'>");
                out.println("<table>");
                out.println("<tr>");
                out.println("<td>First Name:</td>");
                out.println("<td><input type='text' name='firstName' required maxlength='30'></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>Last Name:</td>");
                out.println("<td><input type='text' name='lastName' required maxlength='30'></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td colspan='2'>");
                out.println("<input type='submit' value='Add Author'>");
                out.println("<input type='reset' value='Clear Form'>");
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</form>");
                
            } else {
                // Process form submission
                try {
                    // Get credentials from session
                    HttpSession session = request.getSession();
                    String username = (String) session.getAttribute("username");
                    String password = (String) session.getAttribute("password");
                    
                    if (username == null || password == null) {
                        out.println("<p class='error'>Error: No valid credentials found. Please login again.</p>");
                    } else {
                        // Create credentials and business logic
                        CredentialsDTO creds = new CredentialsDTO();
                        creds.setUsername(username);
                        creds.setPassword(password);
                        
                        AuthorsBusinessLogic logic = new AuthorsBusinessLogic(creds);
                        
                        // Create new author
                        AuthorDTO newAuthor = new AuthorDTO();
                        newAuthor.setFirstName(firstName);
                        newAuthor.setLastName(lastName);
                        
                        // Add author through business logic (includes validation)
                        logic.addAuthor(newAuthor);
                        
                        // Success message
                        out.println("<p class='success'>Author '" + firstName + " " + lastName + "' has been successfully added to the database!</p>");
                        
                        // Show option to add another
                        out.println("<p><a href='addAuthor'>Add Another Author</a></p>");
                    }
                    
                } catch (ValidationException e) {
                    // Handle validation errors
                    out.println("<p class='error'>Validation Error: " + e.getMessage() + "</p>");
                    out.println("<p><a href='addAuthor'>Try Again</a></p>");
                    
                } catch (Exception e) {
                    // Handle other errors
                    out.println("<p class='error'>Error adding author: " + e.getMessage() + "</p>");
                    out.println("<p><a href='addAuthor'>Try Again</a></p>");
                }
            }
            
            out.println("<p><a href='frontController'>Back to Main Menu</a></p>");
            out.println("<div style='text-align: center;'>");
            out.println("<div class='footer-info'>");
            out.println("Program by: Prabhsimran Kaur (041119310) <br>");
            out.println("For: 25S CST8288 Section 012 Assignment 2");
            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
            
        } catch (Exception ex) {
            out.println("<p style='color: red;'>Error: " + ex.getMessage() + "</p>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}