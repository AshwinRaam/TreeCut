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
    <div class="bg-white shadow-lg p-8 max-w-md w-full">
        <h1 class="text-2xl font-bold text-center mb-6">Respond to Quote</h1>

        <!-- Error Messages -->
        <div class="text-red-500 mb-4">
            <p>${errorOne }</p>
            <p>${errorTwo }</p>
        </div>

        <form action="quoterespond" method="post" class="space-y-6">
                <input type="hidden" id="user" name="username" value="${user.username}"/>
                <input type="hidden" id="quoteID" name="quoteID" value="${quote.quoteID}"/>
            <div class="mb-4">
                <label for="modifiedPrice" class="block text-gray-700 text-md font-bold mb-2">Price:</label>
                <input type="number" id="modifiedPrice" step="0.01" name="modifiedPrice"
                       class="shadow border  w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
            </div>

            <div class="mb-4">
                <label for="modifiedStartTime" class="block text-gray-700 text-md font-bold mb-2">Start Time:</label>
                <input type="datetime-local" id="modifiedStartTime" name="modifiedStartTime"
                       class="shadow border  w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
            </div>

            <div class="mb-4">
                <label for="modifiedEndTime" class="block text-gray-700 text-md font-bold mb-2">End Time:</label>
                <input type="datetime-local" id="modifiedEndTime" name="modifiedEndTime"
                       class="shadow border  w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
            </div>

            <div class="mb-4">
                <label for="note" class="block text-gray-700 text-md font-bold mb-2">Note:</label>
                <textarea id="note" rows="5" name="note"
                          class="shadow border  w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"></textarea>
            </div>

            <div class="flex items-center justify-center">
                <input type="submit" value="Submit"
                       class="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4  focus:outline-none focus:shadow-outline w-full">
            </div>
        </form>
    </div>
</div>
</body>
</html>
