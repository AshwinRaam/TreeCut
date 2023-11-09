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
<body class="bg-indigo-300">
<div class="flex justify-center items-center h-screen">
	<form action="login" method="post" class="bg-white shadow-lg px-8 pt-6 pb-8 mb-4">
		<p class="text-red-500 mb-4">${loginFailedStr}</p>
		<div class="mb-4">
			<label for="username" class="block text-gray-700 text-md font-bold mb-2">
				Username:
			</label>
			<input type="text" id="username" name="username" size="45" autofocus
				   class="shadow appearance-none border-b-2  w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline focus:border-b-green-500">
		</div>
		<div class="mb-4">
			<label for="password" class="block text-gray-700 text-md font-bold mb-2">
				Password:
			</label>
			<input type="password" id="password" name="password" size="45"
				   class="shadow appearance-none border-b-2  w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline focus:border-b-green-500">
		</div>
		<div class="flex items-center justify-between">
			<input type="submit" value="Login"
				   class="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4  focus:outline-none focus:shadow-outline w-full">
		</div>
		<div class="flex justify-center">
			<a href="register.jsp" target="_self" class="text-green-500 hover:text-green-700 text-base underline underline-offset-4 my-1 text-center">Register Here</a>
		</div>
	</form>
</div>
</body>
</html>