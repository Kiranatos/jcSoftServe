<%@ page import="com.softserve.itacademy.model.Task" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>List of Tasks</title>

        <style>
            <%@include file="../styles/main.css"%>
        </style>

    </head>
    <body>
        <%@include file="header.html"%>
        <h1>List of Tasks</h1>

        <table border="1">
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Priority</th>
                <th colspan="3">Operation</th>
            </tr>

            <%
                int index = 1;
                for (Task task : (List<Task>) request.getAttribute("tasks")) {                
            %>

            <tr>
                <td><%=index++%></td>
                <td><%=task.getTitle()%></td>
                <td><%=task.getPriority()%></td>
                <td><a href="/read-task?id=<%=task.getId()%>">Read</a></td>
                <td><a href="/edit-task?id=<%=task.getId()%>">Update</a></td>
                <td><a href="/delete-task?id=<%=task.getId()%>">Delete</a></td>
            </tr>
            <%
                }
            %>
        </table>


    </body>
</html>