/*
 * File: DeleteAuthorServlet.java
 * Author: Prabhsimran Kaur (041119310)
 * Date: 2025
 * Description: Servlet to delete author by ID
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
import transferobjects.AuthorDTO;
import transferobjects.CredentialsDTO;

/**
 * Servlet to delete author by ID
 * Handles ID search, confirmation, and author deletion
 * 
 * @author Prabhsimran Kaur (041119310)
 * @version 1.0
 */
public class DeleteAuthorServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Delete Author</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
            out.println("table { border-collapse: collapse; margin: 20px 0; }");
            out.println("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
            out.println("th { background-color: #f2f2f2; }");
            out.println("form { margin: 20px 0; }");
            out.println("input[type='number'] { padding: 5px; margin: 5px; width: 200px; }");
            out.println("input[type='submit'] { padding: 8px 16px; margin: 5px; }");
            out.println(".success { color: green; font-weight: bold; }");
            out.println(".error { color: red; font-weight: bold; }");
            out.println(".warning { color: orange; font-weight: bold; }");
            out.println(".footer-info { margin-top: 40px; font-size: 14px; color: #333; font-family: 'Times New Roman', serif; text-align: left; display: inline-block; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Delete Author</h1>");
            
            String authorIdParam = request.getParameter("authorId");
            String action = request.getParameter("action");
            String confirm = request.getParameter("confirm");
            
            if (authorIdParam == null || authorIdParam.isEmpty()) {
                // Step 1: Show ID search form
                out.println("<p>Enter the Author ID you want to delete:</p>");
                out.println("<form method='post' action='deleteAuthor'>");
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
                // Step 2: Show author details and confirmation form
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
                            out.println("<p class='warning'>Are you sure you want to delete the following author?</p>");
                            out.println("<table>");
                            out.println("<tr>");
                            out.println("<td><strong>AuthorID</strong></td>");
                            out.println("<td><strong>First Name</strong></td>");
                            out.println("<td><strong>Last Name</strong></td>");
                            out.println("</tr>");
                            out.printf("<tr><td>%d</td><td>%s</td><td>%s</td></tr>",
                                author.getAuthorID(), author.getFirstName(), author.getLastName());
                            out.println("</table>");
                            
                            out.println("<p class='warning'><strong>WARNING: This action cannot be undone!</strong></p>");
                            
                            out.println("<form method='post' action='deleteAuthor'>");
                            out.println("<input type='hidden' name='authorId' value='" + authorId + "'>");
                            out.println("<input type='hidden' name='action' value='delete'>");
                            out.println("<input type='hidden' name='confirm' value='yes'>");
                            out.println("<input type='submit' value='YES - Delete Author' style='background-color: #ff4444; color: white;'>");
                            out.println("</form>");
                            
                            out.println("<p><a href='deleteAuthor'>Cancel - Search Another Author</a></p>");
                            
                        } else {
                            out.println("<p class='error'>Error: Author with ID " + authorId + " not found.</p>");
                            out.println("<p><a href='deleteAuthor'>Search Again</a></p>");
                        }
                    }
                    
                } catch (NumberFormatException e) {
                    out.println("<p class='error'>Error: Invalid author ID format.</p>");
                    out.println("<p><a href='deleteAuthor'>Search Again</a></p>");
                } catch (Exception e) {
                    out.println("<p class='error'>Error retrieving author: " + e.getMessage() + "</p>");
                    out.println("<p><a href='deleteAuthor'>Search Again</a></p>");
                }
                
            } else if ("delete".equals(action) && "yes".equals(confirm)) {
                // Step 3: Process deletion
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
                        
                        // First get the author to show what was deleted
                        AuthorDTO author = logic.getAuthor(authorId);
                        
                        if (author != null) {
                            // Delete the author
                            logic.deleteAuthor(author);
                            
                            // Success message
                            out.println("<p class='success'>Author has been successfully deleted!</p>");
                            out.println("<p>Deleted: " + author.getFirstName() + " " + author.getLastName() + " (ID: " + authorId + ")</p>");
                            
                            // Show options
                            out.println("<p><a href='deleteAuthor'>Delete Another Author</a></p>");
                            out.println("<p><a href='getAllAuthors'>View All Authors</a></p>");
                            
                        } else {
                            out.println("<p class='error'>Error: Author with ID " + authorId + " not found.</p>");
                            out.println("<p><a href='deleteAuthor'>Search Again</a></p>");
                        }
                    }
                    
                } catch (NumberFormatException e) {
                    out.println("<p class='error'>Error: Invalid author ID format.</p>");
                    out.println("<p><a href='deleteAuthor'>Search Again</a></p>");
                    
                } catch (Exception e) {
                    out.println("<p class='error'>Error deleting author: " + e.getMessage() + "</p>");
                    out.println("<p><a href='deleteAuthor'>Search Again</a></p>");
                }
                
            } else {
                // Default: show search form
                out.println("<p>Enter the Author ID you want to delete:</p>");
                out.println("<form method='post' action='deleteAuthor'>");
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