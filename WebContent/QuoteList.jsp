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
  <section class="bg-white shadow-lg p-8 mb-4"> <!-- Adjusted width for better alignment -->
    <header class="flex justify-between items-center mb-4"> <!-- Flex container for header -->
      <a href="${pageContext.request.contextPath}/logout" target="_self" class="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 transition duration-300 ease-in-out">Logout</a>
      <h1 class="text-3xl font-bold">Quotes</h1>
      <a href="${pageContext.request.contextPath}/new-quote" target="_self" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 transition duration-300 ease-in-out">Add New Quote</a>
    </header>

    <article>
      <div class="overflow-x-auto">
        <table class="table-auto w-full">
          <thead>
          <tr class="bg-gray-100">
            <th class="px-4 py-2 text-left">Initial Price</th>
            <th class="px-4 py-2 text-left">Current Price</th>
            <th class="px-4 py-2 text-left">Accepted Price</th>
            <th class="px-4 py-2 text-left">Start Time</th>
            <th class="px-4 py-2 text-left">End Time</th>
            <th class="px-4 py-2 text-left">Status</th>
            <th class="px-4 py-2 text-left">Note</th>
            <th class="px-4 py-2 text-left">Respond to Quote</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="quote" items="${listQuotes}">
            <tr class="border-b">
              <td class="px-4 py-2"><c:out value="${quote.initialPrice}" /></td>
              <td class="px-4 py-2"><c:out value="${quote.currentPrice}" /></td>
              <td class="px-4 py-2"><c:out value="${quote.acceptedPrice}" /></td>
              <td class="px-4 py-2"><c:out value="${quote.startTime}" /></td>
              <td class="px-4 py-2"><c:out value="${quote.endTime}" /></td>
              <td class="px-4 py-2"><c:out value="${quote.status}" /></td>
              <td class="px-4 py-2"><c:out value="${quote.note}" /></td>
              <td class="px-4 py-2">
                <form action="showresponses">
                  <input type="submit" value="Respond"
                         class="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 focus:outline-none focus:shadow-outline w-full">
                  <input type="hidden" name="quoteID" value="${quote.quoteID}"/>
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
