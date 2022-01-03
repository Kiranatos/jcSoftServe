<%@ page import="com.softserve.itacademy.model.Priority" %>
<%@ page import="com.softserve.itacademy.model.Task" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create new Task</title>

    <style>
        <%@include file="../styles/main.css"%>
    </style>
    
</head>
<body>
    <%@include file="header.html"%>
    <h1>Create new Task</h1>
    <form action="/create-task " method="post">
    <table>
        <tr>
            <td>
                <label for="title">Name:</label>
            </td>
            <td>
                <input type="text" id="title" name="title">
            </td>
        </tr>
        <tr>
            <td>
                <label for="priority">Priority:</label>
            </td>
            <td>
                <select id="priority" name="priority">
                <option>
                    <%= Priority.LOW%>
                </option>
                <option>
                    <%=Priority.MEDIUM%>
                </option>
                <option>
                    <%=Priority.HIGH%>
                </option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="Create">
            </td>
            <td>
                <input type="reset" value="Clear">
            </td>
        </tr>
    </table>
    </form>

</body>
</html>
