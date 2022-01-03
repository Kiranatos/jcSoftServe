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
        <%@include file="header.html"%>

        <h1>Task with ID &apos;${message}&apos; not found in To-Do List!</h1>

        <h2>url:${url}</h2>
    </body>
</html>
