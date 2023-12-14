<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-indigo-300">

<main class="flex justify-center items-center min-h-screen">
    <section class="bg-white shadow-lg p-8 mb-4 text-center">
        <header class="flex justify-between items-center mb-4">
            <a href="javascript:window.history.back();" class="bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 focus:outline-none focus:shadow-outline">Back to Dashboard</a>
            <h1 class="text-xl font-bold flex-grow text-center">${title}</h1>
            <span class="py-2 px-6"></span> <!-- Placeholder for layout balance -->
        </header>

        <article>
            <h2 class="text-lg font-bold my-4">Client Statistics</h2>
            <div class="overflow-x-auto">
                <table class="table-auto w-full">
                    <thead>
                    <tr class="bg-gray-100">
                        <th class="px-4 py-2 text-left">Client Username</th>
                        <th class="px-4 py-2 text-left">Total Trees</th>
                        <th class="px-4 py-2 text-left">Total Due Amount</th>
                        <th class="px-4 py-2 text-left">Total Paid Amount</th>
                        <th class="px-4 py-2 text-left">View Trees</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="stat" items="${clientStats}">
                        <tr class="border-b">
                            <td class="px-4 py-2"><c:out value="${stat.username}" /></td>
                            <td class="px-4 py-2"><c:out value="${stat.totalTrees}" /></td>
                            <td class="px-4 py-2"><c:out value="${stat.totalDueAmount}" /></td>
                            <td class="px-4 py-2"><c:out value="${stat.totalPaidAmount}" /></td>

                            <td class="px-4 py-2">
                                <form action="view-trees" method="post">
                                    <input type="submit" value="View Trees"
                                           class="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 focus:outline-none focus:shadow-outline w-full">
                                    <input type="hidden" name="username" value="${stat.username}"/>
                                </form>
                            </td>
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
