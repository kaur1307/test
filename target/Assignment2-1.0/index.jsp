<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Author Management System</title>
</head>
<body bgcolor="#FDF5E6">
<center>
    <h2>Login to Author Management System</h2>

    <% String error = request.getParameter("error"); %>
    <% if (error != null && error.equals("true")) { %>
        <p style="color:red;">Invalid username or password. Please try again.</p>
    <% } %>

    <form action="frontController" method="post">
        <table>
            <tr>
                <td>Username:</td>
                <td><input type="text" name="username" required></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="password" required></td>
            </tr>
        </table>

        <p>Choose an action:</p>
        <div>
            <input type="submit" name="action" value="GetAllAuthors">
            <input type="submit" name="action" value="GetAuthorByAuthorId">
            <input type="submit" name="action" value="AddAuthor">
            <input type="submit" name="action" value="UpdateAuthorById">
            <input type="submit" name="action" value="DeleteAuthorById">
        </div>

        <div>
            <br>
            Program by: Prabhsimran Kaur (041119310) <br>
            Section: 25S CST8288 Section 012
        </div>
    </form>
</center>
</body>
</html>