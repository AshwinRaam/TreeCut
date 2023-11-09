<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Responses</title>
</head>
<body>
    <div align="center">
        <h1>Responses</h1>
        <c:if test="${empty listResponses}">
            <c:forEach var="response" items="${listResponses}">
                <table border="1" cellpadding="5">
                    <tr>
                        <th>User:</th>
                        <td>${response.username}</td>
                    </tr>
                <c:if test="${response.modifiedPrice < 0}">
                    <tr>
                        <th>Modified Price:</th>
                        <td>${response.modifiedPrice}</td>
                    </tr>
                </c:if>
                <c:if test="${empty response.modifiedStartTime}">
                    <tr>
                        <th>Modified Start Time:</th>
                        <td>${response.modifiedStartTime}</td>
                    </tr>
                </c:if>
                <c:if test="${empty response.modifiedEndTime}">
                    <tr>
                        <th>Modified End Time:</th>
                        <td>${response.modifiedEndTime}</td>
                    </tr>
                </c:if>
                <c:if test="${empty response.note}">
                    <tr>
                        <th>Note:</th>
                        <td>${response.note}</td>
                    </tr>
                </c:if>
                </table>
            </c:forEach>
        </c:if>
        <form action="createquoteresponse">
            <input type="submit" value="New Response">
            <input type="hidden" name="quoteID" value="${quoteID}">
        </form>
    </div>
</body>
</html>
