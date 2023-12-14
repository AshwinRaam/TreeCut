<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Bill Responses</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-indigo-300">
<main class="flex justify-center items-center min-h-screen">
    <section class="bg-white shadow-xl p-8 max-w-5xl w-full">
        <header class="flex justify-between items-center mb-6">
            <h1 class="text-4xl font-semibold text-gray-800">Bill Responses</h1>
            <a href="javascript:window.history.back();" class="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-6 transition duration-300 ease-in-out shadow">Back to Bills</a>
        </header>

        <div class="overflow-x-auto mb-6 border border-gray-200">
            <table class="table-auto w-full text-gray-700">
                <tr class="bg-gray-100">
                    <th class="px-4 py-2 text-left">Bill ID:</th>
                    <td class="px-4 py-2">${bill.billID}</td>
                </tr>
                <tr>
                    <th class="px-4 py-2 text-left">Order ID:</th>
                    <td class="px-4 py-2">${bill.orderID}</td>
                </tr>
                <tr>
                    <th class="px-4 py-2 text-left">Amount:</th>
                    <td class="px-4 py-2">${bill.amount}</td>
                </tr>
                <tr>
                    <th class="px-4 py-2 text-left">Status:</th>
                    <td class="px-4 py-2">${bill.status}</td>
                </tr>
                <tr>
                    <th class="px-4 py-2 text-left">Created At:</th>
                    <td class="px-4 py-2">${bill.createdAt}</td>
                </tr>
                <!-- Additional bill details can be added here -->
            </table>
        </div>

        <div class="flex justify-between items-center mb-8">
            <!-- New Response Button - Visible only if bill status allows for a new response -->
            <c:if test="${bill.status eq 'Pending' && !isClient}">
                <form action="createbillresponse" class="inline-block">
                    <input type="hidden" name="billID" value="${bill.billID}">
                    <button type="submit" class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-6 transition duration-300 ease-in-out shadow">
                        New Response
                    </button>
                </form>
            </c:if>

            <!-- Additional buttons for actions like payment or dispute can be added here -->

            <c:if test="${isClient}">
                <div>
                    <!-- Pay Bill Button - Visible only if status is Quoted -->
                    <c:if test="${bill.status eq 'Pending'}">
                        <form action="pay-bill" class="inline-block mr-2">
                            <input type="hidden" name="billID" value="${bill.billID}">
                            <button type="submit" class="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-6 transition duration-300 ease-in-out shadow">
                                Pay Bill
                            </button>
                        </form>
                    </c:if>
                </div>
                <!-- Reject Bill Button -->
                <c:if test="${bill.status ne 'Paid'}">
                    <form action="createbillresponse" class="inline-block">
                        <input type="hidden" name="billID" value="${bill.billID}">
                        <button type="submit" class="bg-red-600 hover:bg-red-700 text-white font-bold py-2 px-6 transition duration-300 ease-in-out shadow">
                            Reject Price
                        </button>
                    </form>
                </c:if>
            </c:if>
        </div>

        <div class="overflow-x-auto border border-gray-200">
            <table class="table-auto w-full text-gray-700">
                <thead class="bg-gray-100">
                <tr>
                    <th class="px-6 py-3">Date</th>
                    <th class="px-6 py-3">User</th>
                    <th class="px-6 py-3">Modified Amount</th>
                    <th class="px-6 py-3">Note</th>
                    <!-- Additional headers can be added based on response details -->
                </tr>
                </thead>
                <tbody>
                <c:forEach var="response" items="${listResponses}">
                    <tr class="hover:bg-gray-50">
                        <td class="border px-6 py-4 whitespace-nowrap">${response.createdAt}</td>
                        <td class="border px-6 py-4 whitespace-nowrap">${response.fullName}</td>
                        <td class="border px-6 py-4 whitespace-nowrap">${response.newAmount}</td>
                        <td class="border px-6 py-4 whitespace-nowrap">${response.note}</td>
                        <!-- Additional data cells can be added based on response details -->
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </section>
</main>
</body>
</html>
