/*
 * File: GetAllAuthorsServlet.java
 * Author: Prabhsimran Kaur (041119310)
 * Date: 24 July 2025
 * Description: Servlet to display all authors
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
import java.sql.SQLException;
import transferobjects.AuthorDTO;
import transferobjects.CredentialsDTO;
import java.util.List;

/**
 * Servlet to get and display all authors
 * Shows all authors in a formatted HTML table
 * 
 * @author Prabhsimran Kaur (041119310)
 * @version 1.0
 */
public class GetAllAuthorsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>All Authors</title>");
//            out.println("<style>");
//            out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
//            out.println("table { border-collapse: collapse; width: 100%; margin: 20px 0; }");
//            out.println("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
//            out.println("th { background-color: #f2f2f2; }");
//            out.println(".footer-info { margin-top: 40px; font-size: 14px; color: #333; font-family: 'Times New Roman', serif; text-align: left; display: inline-block; }");
//            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>All Authors at " + request.getContextPath() + "</h1>");

            // Get credentials from session
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");
            String password = (String) session.getAttribute("password");

            if (username == null || password == null) {
                out.println("<p style='color: red;'>Error: No valid credentials found. Please login again.</p>");
                out.println("<p><a href='frontController'>Back to Login</a></p>");
            } else {
                try {
                    CredentialsDTO creds = new CredentialsDTO();
                    creds.setUsername(username);
                    creds.setPassword(password);

                    AuthorsBusinessLogic logic = new AuthorsBusinessLogic(creds);
                    List<AuthorDTO> authors = logic.getAllAuthors();

                    out.println("<table border=\"1\">");
                    out.println("<tr>");
                    out.println("<td><strong>AuthorID</strong></td>");
                    out.println("<td><strong>First Name</strong></td>");
                    out.println("<td><strong>Last Name</strong></td>");
                    out.println("</tr>");
                    
                    for(AuthorDTO author : authors){
                        out.printf("<tr><td>%d</td><td>%s</td><td>%s</td></tr>",
                            author.getAuthorID(), author.getFirstName(), author.getLastName());
                    }
                    out.println("</table>");
                    
                } catch (SQLException ex) {
                    out.println("<p style='color: red;'>Error in Database: " + ex.getMessage() + "</p>");
                }
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
            out.println("<p Error:  " + ex.getMessage() + "</p>");
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