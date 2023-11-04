<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Respond to Quote</title>
</head>
<body>
    <div align="center">
        <p> ${errorOne } </p>
        <p> ${errorTwo } </p>
        <c:set var="user" scope="session" value={user}/>
        <h1>Respond to Quote</h1>
        <form action="quoterespond" method="post">
            <table border="1" cellpadding="5">
            	<tr>
            		<th>Quote ID: </th>
            		<td align="left" colspan="3">
                        <div style="padding-left: 20px">${quote.quoteID}</div>
            			<input type="hidden" name="quoteID" value="${quote.quoteID}"/>
            		</td>
            	</tr>
            	<tr>
            		<th>User: </th>
            		<td align="left" colspan="3">
                        <div style="padding-left: 20px">${user.firstName} ${user.lastName}test</div>
            			<input type="hidden" name="username" value="${user.username}"/>
            		</td>
            	</tr>
                <tr>
                    <th>Price: </th>
                    <td align="center" colspan="3">
                        <input type="number" step="0.01" name="modifiedPrice" size="45">
                    </td>
                </tr>
                <tr>
                	<th>Start time:</th>
                	<td align="center" colspan="3">
                		<input type="datetime-local" name="modifiedStartTime" size="45">
                	</td>
                </tr>
                <tr>
                	<th>End time:</th>
                	<td align="center" colspan="3">
                		<input type="datetime-local" name="modifiedEndTime" size="45">
                	</td>
                </tr>
                <tr>
                	<th>Note:</th>
                	<td align="center" colspan="3">
                		<textarea rows="5" cols="25" name="note"></textarea>
                	</td>
                </tr>
                <tr>
                    <td align="center" colspan="5">
                        <input type="submit" value="Submit">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>