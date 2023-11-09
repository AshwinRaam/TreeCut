<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Client Dashboard</title>
</head>

<center><h1>Welcome! You have been successfully logged in</h1> </center>

 
	<body>
	 <center>
		 <a href="${pageContext.request.contextPath}/quotes" target="_self">Quotes</a>
		 <a href="${pageContext.request.contextPath}/orders" target ="_self">Orders</a>
		 <a href="${pageContext.request.contextPath}/bills" target ="_self">Bills</a>
		 <a href="${pageContext.request.contextPath}/logout" target ="_self" > logout</a><br><br>
		 <p>Client Dashboard Coming Soon....</p>
		 </center>
	</body>
</html>