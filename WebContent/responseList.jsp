<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Responses</title>
</head>
<body>
    <div align="center">
        <h1>Quote: </h1>
        <table table border="1" cellpadding="5">
            <tr>
                <th>Status:</th>
                <td>${quote.status}</td>
            </tr>
            <tr>
                <th>Initial Quoted Price:</th>
                <td>${quote.initialPrice}</td>
            </tr>
            <tr>
                <th>Current Price</th>
                <td>${quote.currentPrice}</td>
            </tr>
            <tr>
                <th>Start/End Time</th>
                <td>${quote.startTime} - ${quote.endTime}</td>
            </tr>
            <tr>
                <th>Notes:</th>
                <td>${quote.note}</td>
            </tr>
            <tr>
                <th>Images:</th>
                <td>
                    <c:forEach var="treePics" items="${picUrls}">
                        <c:forEach var="url" items="${treePics}">
                            <a href="${url}" target="_blank" style="cursor: pointer">
                                <img src="${url}" style="max-width: 100px;height:auto;"/>
                            </a>
                        </c:forEach>
                        <br>
                    </c:forEach>
                </td>
            </tr>
        </table>
        <h1>Responses</h1>
            <c:forEach var="response" items="${listResponses}">
                <table border="1" cellpadding="5">
                    <tr>
                        <th>User:</th>
                        <td>${response.fullName}</td>
                    </tr>
                <c:if test="${response.modifiedPrice > 0}">
                    <tr>
                        <th>Modified Price:</th>
                        <td>${response.modifiedPrice}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty response.modifiedStartTime}">
                    <tr>
                        <th>Modified Start Time:</th>
                        <td>${response.modifiedStartTime}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty response.modifiedEndTime}">
                    <tr>
                        <th>Modified End Time:</th>
                        <td>${response.modifiedEndTime}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty response.note}">
                    <tr>
                        <th>Note:</th>
                        <td>${response.note}</td>
                    </tr>
                </c:if>
                </table>
            </c:forEach>
        <br>
        <form action="createquoteresponse">
            <input type="submit" value="New Response">
            <input type="hidden" name="quoteID" value="${quoteID}">
        </form>
    </div>
</body>
</html>
