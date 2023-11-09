<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="ISO-8859-1">
	<title>Root page</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-indigo-300">

<main class="flex justify-center items-center min-h-screen">
	<section class="bg-white shadow-lg p-8 mb-4 text-center">
		<header class="mb-4">
			<h1 class="text-xl font-bold">Admin Dashboard</h1>
		</header>

		<nav class="mb-4">
			<ul>
				<li>
					<form action="${pageContext.request.contextPath}/initialize">
						<button type="submit" class="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 focus:outline-none focus:shadow-outline">
							Initialize the Database
						</button>
					</form>
				</li>
				<li class="mt-2">
					<a href="${pageContext.request.contextPath}/logout" class="text-green-500 hover:text-green-700 text-sm font-medium">Logout</a>
				</li>
			</ul>
		</nav>

		<article>
			<h2 class="text-lg font-bold my-4">List of Users</h2>
			<div class="overflow-x-auto">
				<table class="table-auto w-full">
					<thead>
					<tr class="bg-gray-100">
						<th class="px-4 py-2 text-left">User ID</th>
						<th class="px-4 py-2 text-left">Email</th>
						<th class="px-4 py-2 text-left">First Name</th>
						<th class="px-4 py-2 text-left">Last Name</th>
						<th class="px-4 py-2 text-left">Address</th>
						<th class="px-4 py-2 text-left">Phone Number</th>
						<th class="px-4 py-2 text-left">Created At</th>
						<th class="px-4 py-2 text-left">Updated At</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach var="user" items="${listUser}">
						<tr class="border-b">
							<td class="px-4 py-2"><c:out value="${user.userID}" /></td>
							<td class="px-4 py-2"><c:out value="${user.email}" /></td>
							<td class="px-4 py-2"><c:out value="${user.firstName}" /></td>
							<td class="px-4 py-2"><c:out value="${user.lastName}" /></td>
							<td class="px-4 py-2"><c:out value="${user.address}" /></td>
							<td class="px-4 py-2"><c:out value="${user.phoneNumber}" /></td>
							<td class="px-4 py-2"><c:out value="${user.createdAt}" /></td>
							<td class="px-4 py-2"><c:out value="${user.updatedAt}" /></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</article>
	</section>
</main>

</body>
</html>
