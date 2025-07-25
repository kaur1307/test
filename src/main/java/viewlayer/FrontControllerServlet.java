package viewlayer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class FrontControllerServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String action = request.getParameter("action");

        // Only allow further actions if login is successful
        if ("cst8288".equals(username) && "cst8288".equals(password)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", "cst8288");

            // Proceed with action only if login is valid
            switch (action) {
                case "GetAllAuthors":
                    request.getRequestDispatcher("/getAllAuthors").forward(request, response);
                    break;
                case "GetAuthorByAuthorId":
                    request.getRequestDispatcher("/getAuthorById").forward(request, response);
                    break;
                case "AddAuthor":
                    request.getRequestDispatcher("/addAuthor").forward(request, response);
                    break;
                case "UpdateAuthorById":
                    request.getRequestDispatcher("/updateAuthor").forward(request, response);
                    break;
                case "DeleteAuthorById":
                    request.getRequestDispatcher("/deleteAuthor").forward(request, response);
                    break;
                default:
                    request.setAttribute("errorMessage", "Invalid action selected.");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } else {
            // Invalid login, forward to index with error message
            request.setAttribute("errorMessage", "Invalid username or password.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}