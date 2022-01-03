<%@ page import="com.softserve.itacademy.model.Task" %>
<%@ page import="com.softserve.itacademy.model.Priority" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit existing Task</title>

    <style>
        <%@include file="../styles/main.css"%>
    </style>

</head>
<body>
    <%@include file="header.html"%>

    <h1>Edit existing Task</h1>

    <%
        Task task = (Task) request.getAttribute("task");
    %>

    <form action="/edit-task" method="post">
        <table>
            <tr>
                <td><label for="Id">Id: </label></td>
                <td><input type="text" id="id" name="id" value="<%=task.getId()%>" disabled></td>
            </tr>
            <tr>
                <td><label for="title">Name: </label></td>
                <td><input type="text" id="title" name="title" value="<%=task.getTitle()%>"></td>
            </tr>
            <tr>
                <td><label for="priority">Priority:</label></td>
                <td>
                    <select name="priority" id="priority" value="<%=task.getPriority()%>">
                        <%
                            for(Priority priority : Priority.values()) {
                                out.println("<option value=\"" + 
                                        priority +
                                        "\">" + 
                                        priority.name().charAt(0) + 
                                        priority.name().substring(1).toLowerCase() + 
                                        "</option>");
                            }
                        %>
                    </select>                        
                </td>
            </tr>
            <tr>
                <td><input type="submit" value="Edit"></td>
                <td><input type="reset" value="Reset"></td>
            </tr>
        </table>
    </form>
                        
</body>
</html>
