<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="ISO-8859-1">
	<title>Contractor Dashboard</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-indigo-300">
<div class="flex flex-col items-center justify-center min-h-screen">
	<div class="text-center">
		<h1 class="text-3xl font-bold text-white mb-8">Contractor Dashboard</h1>
		<div class="bg-white shadow-lg p-8 mb-4">
			<nav class="flex flex-col items-center space-y-4">
				<a href="${pageContext.request.contextPath}/quotes" target="_self" class="dashboard-button bg-green-500 hover:bg-green-600 text-white font-bold py-3 px-6 transition duration-300 ease-in-out w-full text-center">Quotes</a>
				<a href="${pageContext.request.contextPath}/orders" target="_self" class="dashboard-button bg-green-500 hover:bg-green-600 text-white font-bold py-3 px-6 transition duration-300 ease-in-out w-full text-center">Orders</a>
				<a href="${pageContext.request.contextPath}/bills" target="_self" class="dashboard-button bg-green-500 hover:bg-green-600 text-white font-bold py-3 px-6 transition duration-300 ease-in-out w-full text-center">Bills</a>
				<a href="${pageContext.request.contextPath}/logout" target="_self" class="dashboard-button bg-red-500 hover:bg-red-600 text-white font-bold py-3 px-6 transition duration-300 ease-in-out w-full text-center">Logout</a>
			</nav>
		</div>
	</div>
</div>
</body>
</html>
