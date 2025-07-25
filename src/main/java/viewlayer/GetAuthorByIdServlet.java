/*
 * File: GetAuthorByIdServlet.java
 * Author: Prabhsimran Kaur 
 * Student Number: 041119310
 * Description: Servlet to get author by ID
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
 * Servlet to get author by ID
 * Handles search form display and author lookup
 * 
 * @author Prabhsimran Kaur
 * @version 1.0
 */
public class GetAuthorByIdServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Get Author By ID</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
            out.println("table { border-collapse: collapse; width: 100%; margin: 20px 0; }");
            out.println("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
            out.println("th { background-color: #f2f2f2; }");
            out.println("form { margin: 20px 0; }");
            out.println("input[type='number'] { padding: 5px; margin: 5px; }");
            out.println("input[type='submit'] { padding: 8px 16px; margin: 5px; }");
            out.println(".footer-info { margin-top: 40px; font-size: 14px; color: #333; font-family: 'Times New Roman', serif; text-align: left; display: inline-block; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Get Author By ID</h1>");
            
            String authorIdParam = request.getParameter("authorId");
            
            if (authorIdParam == null || authorIdParam.isEmpty()) {
                // Show search form
                out.println("<form method='post' action='getAuthorById'>");
                out.println("Author ID: <input type='number' name='authorId' required min='1'>");
                out.println("<input type='submit' value='Search'>");
                out.println("</form>");
                
            } else {
                // Process search
                try {
                    Integer authorId = Integer.valueOf(authorIdParam);
                    
                    // Get credentials from session
                    HttpSession session = request.getSession();
                    String username = (String) session.getAttribute("username");
                    String password = (String) session.getAttribute("password");
                    
                    if (username == null || password == null) {
                        out.println("<p style='color: red;'>Error: No valid credentials found.</p>");
                    } else {
                        CredentialsDTO creds = new CredentialsDTO();
                        creds.setUsername(username);
                        creds.setPassword(password);
                        
                        AuthorsBusinessLogic logic = new AuthorsBusinessLogic(creds);
                        AuthorDTO author = logic.getAuthor(authorId);
                        
                        if (author != null) {
                            out.println("<table>");
                            out.println("<tr>");
                            out.println("<td><strong>AuthorID</strong></td>");
                            out.println("<td><strong>First Name</strong></td>");
                            out.println("<td><strong>Last Name</strong></td>");
                            out.println("</tr>");
                            out.printf("<tr><td>%d</td><td>%s</td><td>%s</td></tr>",
                                author.getAuthorID(), author.getFirstName(), author.getLastName());
                            out.println("</table>");
                        } else {
                            out.println("<p style='color: red;'>Error: Author with ID " + authorId + " not found.</p>");
                        }
                    }
                } catch (NumberFormatException e) {
                    out.println("<p style='color: red;'>Error: Invalid author ID format.</p>");
                } catch (Exception e) {
                    out.println("<p style='color: red;'>Error: " + e.getMessage() + "</p>");
                }
                
                out.println("<p><a href='getAuthorById'>Search Again</a></p>");
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