/*
 *
 * Author: Prabhsimran Kaur
 * Date: 24 July 2025
 * Description: Servlet to update existing author
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
 * Servlet to update existing author
 * Handles ID search, form display, and author updates with validation
 * 
 * @author Thuvarahan Ragunathan
 * @version 1.0
 */
public class UpdateAuthorServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Update Author</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
            out.println("table { border-collapse: collapse; margin: 20px 0; }");
            out.println("td { padding: 8px; text-align: left; }");
            out.println("form { margin: 20px 0; }");
            out.println("input[type='text'], input[type='number'] { padding: 5px; margin: 5px; width: 200px; }");
            out.println("input[type='submit'] { padding: 8px 16px; margin: 5px; }");
            out.println(".success { color: green; font-weight: bold; }");
            out.println(".error { color: red; font-weight: bold; }");
            out.println(".footer-info { margin-top: 40px; font-size: 14px; color: #333; font-family: 'Times New Roman', serif; text-align: left; display: inline-block; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Update Author</h1>");
            
            String authorIdParam = request.getParameter("authorId");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String action = request.getParameter("action");
            
            if (authorIdParam == null || authorIdParam.isEmpty()) {
                // Step 1: Show ID search form
                out.println("<p>Enter the Author ID you want to update:</p>");
                out.println("<form method='post' action='updateAuthor'>");
                out.println("<table>");
                out.println("<tr>");
                out.println("<td>Author ID:</td>");
                out.println("<td><input type='number' name='authorId' required min='1'></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td colspan='2'>");
                out.println("<input type='hidden' name='action' value='search'>");
                out.println("<input type='submit' value='Find Author'>");
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</form>");
                
            } else if ("search".equals(action)) {
                // Step 2: Show author details and update form
                try {
                    Integer authorId = Integer.valueOf(authorIdParam);
                    
                    // Get credentials from session
                    HttpSession session = request.getSession();
                    String username = (String) session.getAttribute("username");
                    String password = (String) session.getAttribute("password");
                    
                    if (username == null || password == null) {
                        out.println("<p class='error'>Error: No valid credentials found. Please login again.</p>");
                    } else {
                        CredentialsDTO creds = new CredentialsDTO();
                        creds.setUsername(username);
                        creds.setPassword(password);
                        
                        AuthorsBusinessLogic logic = new AuthorsBusinessLogic(creds);
                        AuthorDTO author = logic.getAuthor(authorId);
                        
                        if (author != null) {
                            out.println("<p>Current author details:</p>");
                            out.println("<table border='1'>");
                            out.println("<tr>");
                            out.println("<td><strong>AuthorID</strong></td>");
                            out.println("<td><strong>First Name</strong></td>");
                            out.println("<td><strong>Last Name</strong></td>");
                            out.println("</tr>");
                            out.printf("<tr><td>%d</td><td>%s</td><td>%s</td></tr>",
                                author.getAuthorID(), author.getFirstName(), author.getLastName());
                            out.println("</table>");
                            
                            out.println("<p>Enter new information:</p>");
                            out.println("<form method='post' action='updateAuthor'>");
                            out.println("<table>");
                            out.println("<tr>");
                            out.println("<td>First Name:</td>");
                            out.println("<td><input type='text' name='firstName' value='" + author.getFirstName() + "' required maxlength='30'></td>");
                            out.println("</tr>");
                            out.println("<tr>");
                            out.println("<td>Last Name:</td>");
                            out.println("<td><input type='text' name='lastName' value='" + author.getLastName() + "' required maxlength='30'></td>");
                            out.println("</tr>");
                            out.println("<tr>");
                            out.println("<td colspan='2'>");
                            out.println("<input type='hidden' name='authorId' value='" + authorId + "'>");
                            out.println("<input type='hidden' name='action' value='update'>");
                            out.println("<input type='submit' value='Update Author'>");
                            out.println("<input type='reset' value='Reset Form'>");
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("</table>");
                            out.println("</form>");
                            
                        } else {
                            out.println("<p class='error'>Error: Author with ID " + authorId + " not found.</p>");
                            out.println("<p><a href='updateAuthor'>Search Again</a></p>");
                        }
                    }
                    
                } catch (NumberFormatException e) {
                    out.println("<p class='error'>Error: Invalid author ID format.</p>");
                    out.println("<p><a href='updateAuthor'>Search Again</a></p>");
                } catch (Exception e) {
                    out.println("<p class='error'>Error retrieving author: " + e.getMessage() + "</p>");
                    out.println("<p><a href='updateAuthor'>Search Again</a></p>");
                }
                
            } else if ("update".equals(action) && firstName != null && lastName != null) {
                // Step 3: Process update
                try {
                    Integer authorId = Integer.valueOf(authorIdParam);
                    
                    // Get credentials from session
                    HttpSession session = request.getSession();
                    String username = (String) session.getAttribute("username");
                    String password = (String) session.getAttribute("password");
                    
                    if (username == null || password == null) {
                        out.println("<p class='error'>Error: No valid credentials found. Please login again.</p>");
                    } else {
                        CredentialsDTO creds = new CredentialsDTO();
                        creds.setUsername(username);
                        creds.setPassword(password);
                        
                        AuthorsBusinessLogic logic = new AuthorsBusinessLogic(creds);
                        
                        // Create updated author object
                        AuthorDTO updatedAuthor = new AuthorDTO();
                        updatedAuthor.setAuthorID(authorId);
                        updatedAuthor.setFirstName(firstName);
                        updatedAuthor.setLastName(lastName);
                        
                        // Update author through business logic (includes validation)
                        logic.updateAuthor(updatedAuthor);
                        
                        // Success message
                        out.println("<p class='success'>Author with ID " + authorId + " has been successfully updated!</p>");
                        out.println("<p>Updated to: " + firstName + " " + lastName + "</p>");
                        
                        // Show options
                        out.println("<p><a href='updateAuthor'>Update Another Author</a></p>");
                        out.println("<p><a href='getAllAuthors'>View All Authors</a></p>");
                    }
                    
                } catch (ValidationException e) {
                    // Handle validation errors
                    out.println("<p class='error'>Validation Error: " + e.getMessage() + "</p>");
                    out.println("<p><a href='updateAuthor?action=search&authorId=" + authorIdParam + "'>Try Again</a></p>");
                    
                } catch (NumberFormatException e) {
                    out.println("<p class='error'>Error: Invalid author ID format.</p>");
                    out.println("<p><a href='updateAuthor'>Search Again</a></p>");
                    
                } catch (Exception e) {
                    out.println("<p class='error'>Error updating author: " + e.getMessage() + "</p>");
                    out.println("<p><a href='updateAuthor'>Search Again</a></p>");
                }
                
            } else {
                // Default: show search form
                out.println("<p>Enter the Author ID you want to update:</p>");
                out.println("<form method='post' action='updateAuthor'>");
                out.println("<table>");
                out.println("<tr>");
                out.println("<td>Author ID:</td>");
                out.println("<td><input type='number' name='authorId' required min='1'></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td colspan='2'>");
                out.println("<input type='hidden' name='action' value='search'>");
                out.println("<input type='submit' value='Find Author'>");
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</form>");
            }
            
            out.println("<p><a href='frontController'>Back to Main Menu</a></p>");
            out.println("<div style='text-align: center;'>");
            out.println("<div class='footer-info'>");
            out.println("Program by: Prabhsimran Kaur (041119310)<br>");
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