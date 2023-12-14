<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <title>Client Trees</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-indigo-300">

<main class="flex justify-center items-center min-h-screen">
    <section class="bg-white shadow-lg p-8 mb-4 text-center">
        <header class="flex justify-between items-center mb-4">
            <a href="javascript:window.history.back();" class="bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 focus:outline-none focus:shadow-outline">Back to Dashboard</a>
            <h1 class="text-xl font-bold flex-grow text-center">Client Trees</h1>
            <span class="py-2 px-6"></span> <!-- Placeholder for layout balance -->
        </header>

        <article>
            <div class="overflow-x-auto">
                <table class="table-auto w-full">
                    <thead>
                    <tr class="bg-gray-100">
                        <th class="px-4 py-2 text-left">Tree ID</th>
                        <th class="px-4 py-2 text-left">Quote ID</th>
                        <th class="px-4 py-2 text-left">Size</th>
                        <th class="px-4 py-2 text-left">Height</th>
                        <th class="px-4 py-2 text-left">Location</th>
                        <th class="px-4 py-2 text-left">Near House</th>
                        <th class="px-4 py-2 text-left">Completion Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="tree" items="${listTrees}">
                        <tr class="border-b">
                            <td class="px-4 py-2"><c:out value="${tree.id}" /></td>
                            <td class="px-4 py-2"><c:out value="${tree.quoteID}" /></td>
                            <td class="px-4 py-2"><c:out value="${tree.size}" /></td>
                            <td class="px-4 py-2"><c:out value="${tree.height}" /></td>
                            <td class="px-4 py-2"><c:out value="${tree.location}" /></td>
                            <td class="px-4 py-2"><c:out value="${tree.nearHouse}" /></td>
                            <td class="px-4 py-2"><c:out value="${tree.updatedAt}" /></td>
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
