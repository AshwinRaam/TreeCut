<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <title>Bills Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-indigo-300">

<main class="flex justify-center items-center min-h-screen">
    <section class="bg-white shadow-lg p-8 mb-4">
        <header class="flex justify-between items-center mb-4">
            <a href="${pageContext.request.contextPath}/login" target="_self" class="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-6 transition duration-300 ease-in-out shadow">Back to Dashboard</a>
            <h1 class="text-3xl font-bold text-center flex-1">Bills</h1>
        </header>
        <article>
            <div class="overflow-x-auto">
                <table class="table-auto w-full">
                    <thead>
                    <tr class="bg-gray-100">
                        <th class="px-4 py-2 text-left">Bill ID</th>
                        <th class="px-4 py-2 text-left">Order ID</th>
                        <th class="px-4 py-2 text-left">Amount</th>
                        <th class="px-4 py-2 text-left">Status</th>
                        <th class="px-4 py-2 text-left">Created At</th>
                        <th class="px-4 py-2 text-left">Respond to Bill</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="bill" items="${listBills}">
                        <tr class="border-b">
                            <td class="px-4 py-2"><c:out value="${bill.billID}" /></td>
                            <td class="px-4 py-2"><c:out value="${bill.orderID}" /></td>
                            <td class="px-4 py-2"><c:out value="${bill.amount}" /></td>
                            <td class="px-4 py-2"><c:out value="${bill.status}" /></td>
                            <td class="px-4 py-2"><c:out value="${bill.createdAt}" /></td>
                            <td class="px-4 py-2">
                                <!-- View Quote Button -->
                                <form action="viewQuote" method="get">
                                    <input type="hidden" name="orderID" value="${bill.orderID}" />
                                    <input type="submit" value="View Quote"
                                           class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 focus:outline-none focus:shadow-outline">
                                </form>
                            </td>
                            <td class="px-4 py-2">
                                <form action="showbillresponses" method="get">
                                    <input type="hidden" name="billID" value="${bill.billID}" />
                                    <input type="submit" value="View"
                                           class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 focus:outline-none focus:shadow-outline">
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
