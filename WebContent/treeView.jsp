<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>View Tree</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-indigo-300">
<main class="flex justify-center items-center min-h-screen">
    <section class="bg-white shadow-xl p-8 max-w-3xl w-full">
        <header class="flex justify-between items-center mb-6">
            <h1 class="text-4xl font-semibold text-gray-800">View Tree</h1>
            <form action="showresponses" method="post" class="inline-block">
                <input type="hidden" name="quoteID" value="${tree.quoteID}">
                <button type="submit" class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-6 transition duration-300 ease-in-out shadow">
                    Back
                </button>
            </form>
        </header>

        <div class="overflow-x-auto border border-gray-200">
            <table class="table-auto w-full text-gray-700">
                <tr class="bg-gray-100">
                    <th class="px-4 py-2 text-left">Size:</th>
                    <td class="px-4 py-2">${tree.size}</td>
                </tr>
                <tr>
                    <th class="px-4 py-2 text-left">Height:</th>
                    <td class="px-4 py-2">${tree.height}</td>
                </tr>
                <tr>
                    <th class="px-4 py-2 text-left">Location:</th>
                    <td class="px-4 py-2">${tree.location}</td>
                </tr>
                <tr>
                    <th class="px-4 py-2 text-left">Near House:</th>
                    <td class="px-4 py-2">${tree.nearHouse}</td>
                </tr>
                <tr>
                    <th class="px-4 py-2 text-left">Images:</th>
                    <td class="px-4 py-2 flex space-x-2">
                        <img src="${pageContext.request.contextPath}${tree.pictureURL1}" class="max-w-xs h-auto rounded"/>
                        <img src="${pageContext.request.contextPath}${tree.pictureURL2}" class="max-w-xs h-auto rounded"/>
                        <img src="${pageContext.request.contextPath}${tree.pictureURL3}" class="max-w-xs h-auto rounded"/>
                    </td>
                </tr>
            </table>
        </div>
    </section>
</main>
</body>
</html>
