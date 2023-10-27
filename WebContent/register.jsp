<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Registration</title>
</head>
<body>
    <div align="center">
        <p> ${errorOne } </p>
        <p> ${errorTwo } </p>
        <form action="register" method="post">
            <table border="1" cellpadding="5">
                <tr>
                    <th>Username: </th>
                    <td align="center" colspan="3">
                        <input type="text" name="username" size="45" required>
                    </td>
                </tr>
                <tr>
                    <th>Email: </th>
                    <td align="center" colspan="3">
                        <input type="email" name="email" size="45" required>
                    </td>
                </tr>
                <tr>
                    <th>Password: </th>
                    <td align="center" colspan="3">
                        <input type="password" name="password" size="45" required>
                    </td>
                </tr>
                <tr>
                    <th>Retype Password: </th>
                    <td align="center" colspan="3">
                        <input type="password" name="retypePassword" size="45" required>
                    </td>
                </tr>
                <tr>
                    <th>First Name: </th>
                    <td align="center" colspan="3">
                        <input type="text" name="firstName" size="45" required>
                    </td>
                </tr>
                <tr>
                    <th>Last Name: </th>
                    <td align="center" colspan="3">
                        <input type="text" name="lastName" size="45" required>
                    </td>
                </tr>
                <tr>
                    <th>Address: </th>
                    <td align="center" colspan="3">
                        <input type="text" name="address" size="45" required>
                    </td>
                </tr>
                <tr>
                    <th>Phone Number: </th>
                    <td align="center" colspan="3">
                        <input type="tel" name="phoneNumber" size="45" required>
                    </td>
                </tr>
                <tr>
                    <th>Credit Card Information: </th>
                    <td align="center" colspan="3">
                        <input type="text" name="creditCardInfo" size="45">
                    </td>
                </tr>
                <tr>
                    <th>Role: </th>
                    <td align="center" colspan="3">
                        <select name="role" required>
                            <option value="Client">Client</option>
                            <option value="Contractor">Contractor</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="center" colspan="5">
                        <input type="submit" value="Register">
                    </td>
                </tr>
            </table>
            <a href="login.jsp" target="_self">Return to Login Page</a>
        </form>
    </div>
</body>
</html>
