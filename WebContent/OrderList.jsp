<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <title>Orders Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-indigo-300">

<main class="flex justify-center items-center min-h-screen">
    <section class="bg-white shadow-lg p-8 mb-4">
        <header class="flex justify-between items-center mb-4">
            <a href="javascript:window.history.back();" target="_self" class="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-6 transition duration-300 ease-in-out shadow">Back to Dashboard</a>
            <h1 class="text-3xl font-bold text-center flex-1">Orders</h1>
            <span class="py-2 px-6"></span>
        </header>

        <article>
            <div class="overflow-x-auto">
                <table class="table-auto w-full">
                    <thead>
                    <tr class="bg-gray-100">
                        <th class="px-4 py-2 text-left">Order ID</th>
                        <th class="px-4 py-2 text-left">Quote ID</th>
                        <th class="px-4 py-2 text-left">Status</th>
                        <th class="px-4 py-2 text-left">Created At</th>
                        <th class="px-4 py-2 text-left">View Quote</th> <!-- New column for actions -->
                        <c:if test="${!user.isClient()}">
                        <th class="px-4 py-2 text-left">Complete Order</th>
                        </c:if>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="order" items="${listOrders}">
                        <tr class="border-b">
                            <td class="px-4 py-2"><c:out value="${order.orderID}" /></td>
                            <td class="px-4 py-2"><c:out value="${order.quoteID}" /></td>
                            <td class="px-4 py-2"><c:out value="${order.status}" /></td>
                            <td class="px-4 py-2"><c:out value="${order.createdAt}" /></td>
                            <td class="px-4 py-2">
                                <!-- Complete Button Form -->
                                <form action="showresponses" method="post">
                                    <input type="hidden" name="quoteID" value="${order.quoteID}" />
                                    <input type="submit" value="View Quote"
                                           class="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 focus:outline-none focus:shadow-outline">
                                </form>
                            </td>
                            <c:if test="${!user.isClient()}">
                            <c:if test="${order.status ne 'Completed'}">
                            <td class="px-4 py-2">
                                <!-- Complete Button Form -->
                                <form action="complete-order" method="post">
                                    <input type="hidden" name="orderID" value="${order.orderID}" />
                                    <input type="submit" value="Complete"
                                           class="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 focus:outline-none focus:shadow-outline">
                                </form>
                            </td>
                            </c:if>
                            </c:if>
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
