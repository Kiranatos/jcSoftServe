<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error Page</title>

    <style>
        <%@include file="../styles/main.css"%>
    </style>

</head>
<body>

    <%
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
    %>

    <%@ include file="header.html"%>
    <br>

    <%
        if (statusCode != 500) {
    %>
        <h2>code: <%=statusCode%></h2>
        <h3>url: <%=requestUri%></h3>
    <%
        } else {
    %>
        <h2><%=exception.getMessage()%></h2>
        <h3>url: <%=requestUri%></h3>
    <%
        }
    %>

</body>
</html>