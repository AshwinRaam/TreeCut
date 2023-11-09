<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration - Tree Cut</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-indigo-300">
<div class="flex justify-center items-center h-screen">
    <form action="register" method="post" class="bg-white shadow-lg px-8 pt-6 pb-8">
        <!-- Error Messages -->
        <p class="text-red-500 mb-4">${errorOne }</p>
        <p class="text-red-500 mb-4">${errorTwo }</p>

        <!-- Input Fields -->
        <div class="mb-4">
            <label for="username" class="block text-gray-700 text-md font-bold mb-2">
                Username:
            </label>
            <input type="text" id="username" name="username" size="45"
                   class="shadow appearance-none border-b-2 w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline focus:border-b-green-500" required>
        </div>

        <div class="mb-4">
            <label for="email" class="block text-gray-700 text-md font-bold mb-2">
                Email:
            </label>
            <input type="text" id="email" name="email" size="45"
                   class="shadow appearance-none border-b-2 w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline focus:border-b-green-500" required>
        </div>

        <div class="mb-4">
            <label for="password" class="block text-gray-700 text-md font-bold mb-2">
                Password:
            </label>
            <input type="password" id="password" name="password" size="45"
                   class="shadow appearance-none border-b-2 w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline focus:border-b-green-500" required>
        </div>

        <div class="mb-4">
            <label for="retypePassword" class="block text-gray-700 text-md font-bold mb-2">
                Retype Password:
            </label>
            <input type="password" id="retypePassword" name="retypePassword" size="45"
                   class="shadow appearance-none border-b-2 w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline focus:border-b-green-500" required>
        </div>

        <div class="mb-4">
            <label for="firstName" class="block text-gray-700 text-md font-bold mb-2">
                First Name:
            </label>
            <input type="text" id="firstName" name="firstName" size="45"
                   class="shadow appearance-none border-b-2 w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline focus:border-b-green-500" required>
        </div>

        <div class="mb-4">
            <label for="lastName" class="block text-gray-700 text-md font-bold mb-2">
                Last Name:
            </label>
            <input type="text" id="lastName" name="lastName" size="45"
                   class="shadow appearance-none border-b-2 w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline focus:border-b-green-500" required>
        </div>

        <div class="mb-4">
            <label for="address" class="block text-gray-700 text-md font-bold mb-2">
                Address:
            </label>
            <input type="text" id="address" name="address" size="45"
                   class="shadow appearance-none border-b-2 w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline focus:border-b-green-500" required>
        </div>


        <div class="mb-4">
            <label for="phoneNumber" class="block text-gray-700 text-md font-bold mb-2">
                Phone Number:
            </label>
            <input type="text" id="phoneNumber" name="phoneNumber" size="45"
                   class="shadow appearance-none border-b-2 w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline focus:border-b-green-500" required>
        </div>

        <div class="mb-4">
            <label for="creditCardInfo" class="block text-gray-700 text-md font-bold mb-2">
                Credit Card Information:
            </label>
            <input type="text" id="creditCardInfo" name="creditCardInfo" size="45"
                   class="shadow appearance-none border-b-2 w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline focus:border-b-green-500" required>
        </div>


        <!-- Role Selection -->
        <div class="mb-4">
            <label for="role" class="block text-gray-700 text-md font-bold mb-2">
                Role:
            </label>
            <select id="role" name="role"
                    class="block appearance-none w-full bg-white border border-gray-200 text-gray-700 py-3 px-4 pr-8 leading-tight focus:outline-none focus:bg-white focus:border-green-500" required>
                <option value="Client">Client</option>
                <option value="Contractor">Contractor</option>
            </select>
        </div>

        <!-- Submit Button -->
        <div class="flex items-center justify-between">
            <input type="submit" value="Register"
                   class="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 focus:outline-none focus:shadow-outline w-full">
        </div>

        <!-- Login Link -->
        <div class="flex justify-center">
            <a href="login.jsp" target="_self" class="text-green-500 hover:text-green-700 text-base underline underline-offset-4 my-1 text-center">Return to Login Page</a>
        </div>
    </form>
</div>
</body>
</html>
