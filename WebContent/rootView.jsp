<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Root page</title>
</head>
<body>

<div align="center">
	<form action="initialize">
		<input type="submit" value="Initialize the Database"/>
	</form>
	<a href="login.jsp" target="_self">logout</a><br><br> 

	<h1>List all users</h1>
	<div align="center">
		<table border="1" cellpadding="6">
			<caption><h2>List of Users</h2></caption>
			<tr>
				<th>User ID</th>
				<th>Email</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Address</th>
				<th>Phone Number</th>
				<th>Created At</th>
				<th>Updated At</th>
			</tr>
			<c:forEach var="user" items="${listUser}">
				<tr style="text-align:center">
					<td><c:out value="${user.userID}" /></td>
					<td><c:out value="${user.email}" /></td>
					<td><c:out value="${user.firstName}" /></td>
					<td><c:out value="${user.lastName}" /></td>
					<td><c:out value="${user.address}" /></td>
					<td><c:out value="${user.phoneNumber}" /></td>
					<td><c:out value="${user.createdAt}" /></td>
					<td><c:out value="${user.updatedAt}" /></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>

</body>
</html>