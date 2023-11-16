<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Tree</title>
</head>
<body>
<body>
    <div align="center">
        <h1>View Tree:</h1>
        <form action="showresponses">
            <input type="hidden" name="quoteID" value="${tree.quoteID}">
            <input type="submit" value="Back">
        </form>
        <table table border="1" cellpadding="5">
            <tr>
                <th>Size:</th>
                <td>${tree.size}</td>
            </tr>
            <tr>
                <th>Height:</th>
                <td>${tree.height}</td>
            </tr>
            <tr>
                <th>Location:</th>
                <td>${tree.location}</td>
            </tr>
            <tr>
                <th>Is it near the house?</th>
                <td>${tree.nearHouse}</td>
            </tr>
            <tr>
                <th>Images:</th>
                <td>
                    <img src="${pageContext.request.contextPath}${tree.pictureURL1}" style="max-width: 100px;height:auto;">
                    <img src="${pageContext.request.contextPath}${tree.pictureURL2}" style="max-width: 100px;height:auto;">
                    <img src="${pageContext.request.contextPath}${tree.pictureURL3}" style="max-width: 100px;height:auto;">
                </td>
            </tr>
        </table>
    </div>
</body>
</html>
