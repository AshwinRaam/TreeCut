<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Respond to Quote</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-indigo-300">
<div class="flex justify-center items-center min-h-screen">
    <div class="bg-white shadow-lg px-8 pt-6 pb-8 mb-4">
        <h1 class="text-xl font-bold text-center mb-4">Respond to Quote</h1>

        <!-- Error Messages -->
        <p class="text-red-500 mb-4">${errorOne }</p>
        <p class="text-red-500 mb-4">${errorTwo }</p>

        <form action="quoterespond" method="post">
            <div class="mb-4">
                <label for="user" class="block text-gray-700 text-md font-bold mb-2">User:</label>
                <div class="pl-4">${user.firstName} ${user.lastName}</div>
                <input type="hidden" id="user" name="username" value="${user.username}"/>
            </div>

            <div class="mb-4">
                <label for="modifiedPrice" class="block text-gray-700 text-md font-bold mb-2">Price:</label>
                <input type="number" id="modifiedPrice" step="0.01" name="modifiedPrice" size="45"
                       class="shadow appearance-none border-b-2 w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline focus:border-b-green-500">
            </div>

            <div class="mb-4">
                <label for="modifiedStartTime" class="block text-gray-700 text-md font-bold mb-2">Start time:</label>
                <input type="datetime-local" id="modifiedStartTime" name="modifiedStartTime"
                       class="shadow appearance-none border-b-2 w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline focus:border-b-green-500">
            </div>

            <div class="mb-4">
                <label for="modifiedEndTime" class="block text-gray-700 text-md font-bold mb-2">End time:</label>
                <input type="datetime-local" id="modifiedEndTime" name="modifiedEndTime"
                       class="shadow appearance-none border-b-2 w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline focus:border-b-green-500">
            </div>

            <div class="mb-4">
                <label for="note" class="block text-gray-700 text-md font-bold mb-2">Note:</label>
                <textarea id="note" rows="5" name="note"
                          class="shadow appearance-none border-b-2 w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline focus:border-b-green-500"></textarea>
            </div>

            <div class="flex items-center justify-center">
                <input type="submit" value="Submit"
                       class="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 focus:outline-none focus:shadow-outline w-full">
            </div>
        </form>
    </div>
</div>
</body>
</html>
