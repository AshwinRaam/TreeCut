<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <title>Root Dashboard</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-indigo-300">
<div class="flex flex-col items-center justify-center min-h-screen">
    <div class="text-center">
        <h1 class="text-3xl font-bold text-white mb-8">Root Dashboard</h1>
        <div class="bg-white shadow-lg p-8 mb-4">
            <nav class="flex flex-col items-center space-y-4">
                <a href="${pageContext.request.contextPath}/big-clients" class="dashboard-button bg-blue-500 hover:bg-blue-600 text-white font-bold py-3 px-6 transition duration-300 ease-in-out w-full text-center">Big Clients</a>
                <a href="${pageContext.request.contextPath}/easy-clients" class="dashboard-button bg-blue-500 hover:bg-blue-600 text-white font-bold py-3 px-6 transition duration-300 ease-in-out w-full text-center">Easy Clients</a>
                <a href="${pageContext.request.contextPath}/one-tree-quotes" class="dashboard-button bg-blue-500 hover:bg-blue-600 text-white font-bold py-3 px-6 transition duration-300 ease-in-out w-full text-center">One Tree Quotes</a>
                <a href="${pageContext.request.contextPath}/prospective-clients" class="dashboard-button bg-blue-500 hover:bg-blue-600 text-white font-bold py-3 px-6 transition duration-300 ease-in-out w-full text-center">Prospective Clients</a>
                <a href="${pageContext.request.contextPath}/highest-tree" class="dashboard-button bg-blue-500 hover:bg-blue-600 text-white font-bold py-3 px-6 transition duration-300 ease-in-out w-full text-center">Highest Tree</a>
                <a href="${pageContext.request.contextPath}/overdue-bills" class="dashboard-button bg-blue-500 hover:bg-blue-600 text-white font-bold py-3 px-6 transition duration-300 ease-in-out w-full text-center">Overdue Bills</a>
                <a href="${pageContext.request.contextPath}/bad-clients" class="dashboard-button bg-blue-500 hover:bg-blue-600 text-white font-bold py-3 px-6 transition duration-300 ease-in-out w-full text-center">Bad Clients</a>
                <a href="${pageContext.request.contextPath}/good-clients" class="dashboard-button bg-blue-500 hover:bg-blue-600 text-white font-bold py-3 px-6 transition duration-300 ease-in-out w-full text-center">Good Clients</a>
                <a href="${pageContext.request.contextPath}/statistics" class="dashboard-button bg-blue-500 hover:bg-blue-600 text-white font-bold py-3 px-6 transition duration-300 ease-in-out w-full text-center">Statistics</a>
                <a href="${pageContext.request.contextPath}/reset" class="dashboard-button bg-yellow-500 hover:bg-yellow-600 text-white font-bold py-3 px-6 transition duration-300 ease-in-out w-full text-center">Reset</a>
                <a href="${pageContext.request.contextPath}/sign-out" class="dashboard-button bg-red-500 hover:bg-red-600 text-white font-bold py-3 px-6 transition duration-300 ease-in-out w-full text-center">Sign Out</a>
            </nav>
        </div>
    </div>
</div>
</body>
</html>
