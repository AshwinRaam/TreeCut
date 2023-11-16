<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Responses</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-indigo-300">
<main class="flex justify-center items-center min-h-screen">
    <section class="bg-white shadow-xl p-8 max-w-5xl w-full">
        <header class="flex justify-between items-center mb-6">
            <h1 class="text-4xl font-semibold text-gray-800">Quote Responses</h1>
            <a href="${pageContext.request.contextPath}/quotes" class="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-6 transition duration-300 ease-in-out shadow">Back to Quotes</a>
        </header>

        <div class="overflow-x-auto mb-6 border border-gray-200">
            <table class="table-auto w-full text-gray-700">
                <tr class="bg-gray-100">
                    <th class="px-4 py-2 text-left">Status:</th>
                    <td class="px-4 py-2">${quote.status}</td>
                </tr>
                <tr>
                    <th class="px-4 py-2 text-left">Initial Quoted Price:</th>
                    <td class="px-4 py-2">${quote.initialPrice}</td>
                </tr>
                <tr>
                    <th class="px-4 py-2 text-left">Current Price:</th>
                    <td class="px-4 py-2">${quote.currentPrice}</td>
                </tr>
                <tr>
                    <th class="px-4 py-2 text-left">Start/End Time:</th>
                    <td class="px-4 py-2">${quote.startTime} - ${quote.endTime}</td>
                </tr>
                <tr>
                    <th class="px-4 py-2 text-left">Notes:</th>
                    <td class="px-4 py-2">${quote.note}</td>
                </tr>
                <tr>
                    <th class="px-4 py-2 text-left">Images:</th>
                    <td class="px-4 py-2">
                        <c:forEach var="treePics" items="${picUrls}">
                            <c:forEach var="url" items="${treePics}">
                                <a href="${pageContext.request.contextPath}${url}" target="_blank" class="inline-block mr-2">
                                    <img src="${pageContext.request.contextPath}${url}" class="max-w-xs h-auto "/>
                                </a>
                            </c:forEach>
                            <br>
                        </c:forEach>
                    </td>
                </tr>
            </table>
        </div>

        <div class="flex justify-between items-center mb-8">
            <!-- New Response Button on the Left -->
            <form action="createquoteresponse" class="inline-block">
                <input type="hidden" name="quoteID" value="${quoteID}">
                <button type="submit" class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-6 transition duration-300 ease-in-out shadow">
                    New Response
                </button>
            </form>

            <!-- Accept and Reject Quote Buttons on the Right -->
            <div>
                <form action="accept-quote" class="inline-block mr-2">
                    <input type="hidden" name="quoteID" value="${quoteID}">
                    <button type="submit" class="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-6 transition duration-300 ease-in-out shadow">
                        Accept Quote
                    </button>
                </form>
                <form action="reject-quote" class="inline-block">
                    <input type="hidden" name="quoteID" value="${quoteID}">
                    <button type="submit" class="bg-red-600 hover:bg-red-700 text-white font-bold py-2 px-6 transition duration-300 ease-in-out shadow">
                        Reject Quote
                    </button>
                </form>
            </div>
        </div>

        <div class="overflow-x-auto  border border-gray-200">
            <table class="table-auto w-full text-gray-700">
                <thead class="bg-gray-100">
                <tr>
                    <th class="px-6 py-3">Date</th>
                    <th class="px-6 py-3">User</th>
                    <th class="px-6 py-3">Modified Price</th>
                    <th class="px-6 py-3">Modified Start Time</th>
                    <th class="px-6 py-3">Modified End Time</th>
                    <th class="px-6 py-3">Note</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="response" items="${listResponses}">
                    <tr class="hover:bg-gray-50">
                        <td class="border px-6 py-4 whitespace-nowrap">${response.createdAt}</td>
                        <td class="border px-6 py-4 whitespace-nowrap">${response.fullName}</td>
                        <td class="border px-6 py-4 whitespace-nowrap">${response.modifiedPrice}</td>
                        <td class="border px-6 py-4 whitespace-nowrap">${response.modifiedStartTime}</td>
                        <td class="border px-6 py-4 whitespace-nowrap">${response.modifiedEndTime}</td>
                        <td class="border px-6 py-4 whitespace-nowrap">${response.note}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </section>
</main>
</body>
</html>
