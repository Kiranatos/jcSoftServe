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

    <%
        Task task = (Task) request.getAttribute("task");
        String message = (String) request.getAttribute("error");
    %>
    
    <h2>Create new Task</h2>
    
    <p><%=message != null ? message : ""%></p>

    <form action="" method="post">
        <table>
            <tr>
                <td>
                    <label for="title">Name: </label>
                </td>
                <td>
                    <%
                        if (message != null) {
                    %>
                        <input type="text" name="title" id="title" value="<%= task.getTitle()%>" autocomplete="off">
                    <%
                        } else {
                    %>
                        <input type="text" name="title" id="title" autocomplete="off">
                    <%
                        }
                    %>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="priority">Priority: </label>
                </td>
                <td>
                    <%
                        if (message != null) {
                    %>
                    <select name="priority" id="priority">
                        <%
                            for (Priority priority : Priority.values()) {
                                if (priority.name().equals(task.getPriority().name())) {
                        %>
                        <option value="<%= priority.name()%>" selected>
                            <%= priority.name().charAt(0) + priority.name().substring(1).toLowerCase()%>
                        </option>
                        <%
                        } else {
                        %>
                        <option value="<%= priority.name()%>">
                            <%= priority.name().charAt(0) + priority.name().substring(1).toLowerCase()%>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                    <%
                    } else {
                    %>
                    <select name="priority" id="priority">
                        <%
                            for (Priority priority : Priority.values()) {
                        %>
                        <option value="<%= priority.name()%>">
                            <%= priority.name().charAt(0) + priority.name().substring(1).toLowerCase()%>
                        </option>
                        <%
                            }
                        %>
                    </select>
                    <%
                        }
                    %>
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
