<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Login - Tree Cut</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
<%--	<h1 class="text-3xl font-bold text-center">Welcome to TreeCut Login page</h1>--%>
<div class="flex justify-center mt-10">
	<form action="login" method="post" class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
		<p class="text-red-500 mb-4">${loginFailedStr}</p>
		<table class="table-auto">
			<tr>
				<th class="text-left">Username:</th>
				<td ><label>
					<input type="text" name="username" size="45" autofocus class="border-2 rounded my-1 p-2 w-full">
				</label></td>
			</tr>
			<tr>
				<th class="text-left">Password:</th>
				<td ><label>
					<input type="password" name="password" size="45" class="border-2 rounded my-1 p-2 w-full">
				</label></td>
			</tr>
			<tr>
				<td colspan="4" class="text-center">
					<input type="submit" value="Login" class="bg-blue-500 hover:bg-blue-700 text-white font-bold my-2 py-2 px-4 rounded w-full">
				</td>
			</tr>
		</table>
		<div class="flex justify-center">
			<a href="register.jsp" target="_self" class="text-blue-500 hover:text-blue-700 text-base underline underline-offset-4 my-1 text-center">Register Here</a>
		</div>
	</form>
</div>
</body>
</html>
