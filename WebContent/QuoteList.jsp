<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="ISO-8859-1">
  <title>Quotes page</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-indigo-300">

<main class="flex justify-center items-center min-h-screen">
  <section class="bg-white shadow-lg p-8 mb-4 text-center">
    <header class="mb-4">
      <h1 class="text-xl font-bold">Quotes</h1>
    </header>

    <nav class="mb-4">
      <ul>
        <li class="mt-2">
          <a href="${pageContext.request.contextPath}/logout" class="text-green-500 hover:text-green-700 text-sm font-medium">Logout</a>
        </li>
      </ul>
    </nav>

    <article>
      <h2 class="text-lg font-bold my-4">List of Quotes</h2>
      <div class="overflow-x-auto">
        <table class="table-auto w-full">
          <thead>
          <tr class="bg-gray-100">
            <th class="px-4 py-2 text-left">Quote ID</th>
            <th class="px-4 py-2 text-left">Client ID</th>
            <th class="px-4 py-2 text-left">Contractor ID</th>
            <th class="px-4 py-2 text-left">Initial Price</th>
            <th class="px-4 py-2 text-left">Current Price</th>
            <th class="px-4 py-2 text-left">Accepted Price</th>
            <th class="px-4 py-2 text-left">Start Time</th>
            <th class="px-4 py-2 text-left">End Time</th>
            <th class="px-4 py-2 text-left">Status</th>
            <th class="px-4 py-2 text-left">Note</th>
            <th class="px-4 py-2 text-left">Created At</th>
            <th class="px-4 py-2 text-left">Updated At</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="quote" items="${listQuotes}">
            <tr class="border-b">
              <td class="px-4 py-2"><c:out value="${quote.quoteID}" /></td>
              <td class="px-4 py-2"><c:out value="${quote.clientID}" /></td>
              <td class="px-4 py-2"><c:out value="${quote.contractorID}" /></td>
              <td class="px-4 py-2"><c:out value="${quote.initialPrice}" /></td>
              <td class="px-4 py-2"><c:out value="${quote.currentPrice}" /></td>
              <td class="px-4 py-2"><c:out value="${quote.acceptedPrice}" /></td>
              <td class="px-4 py-2"><c:out value="${quote.startTime}" /></td>
              <td class="px-4 py-2"><c:out value="${quote.endTime}" /></td>
              <td class="px-4 py-2"><c:out value="${quote.status}" /></td>
              <td class="px-4 py-2"><c:out value="${quote.note}" /></td>
              <td class="px-4 py-2"><c:out value="${quote.createdAt}" /></td>
              <td class="px-4 py-2"><c:out value="${quote.updatedAt}" /></td>
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
