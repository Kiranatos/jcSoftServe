package com.softserve.itacademy.controller;

import com.softserve.itacademy.repository.TaskRepository;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/delete-task")
public class DeleteTaskServlet extends HttpServlet {

    private TaskRepository taskRepository;

    @Override
    public void init()  {
        taskRepository = TaskRepository.getTaskRepository();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));

        boolean isDeleted = taskRepository.delete(id);

        if (isDeleted) {
            response.sendRedirect(request.getContextPath() + "/tasks-list");
        } else {
            response.setStatus(404);
            request.setAttribute("message", "Task with ID " + id + " not found!");
            request.setAttribute("url", request.getServletPath());
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }
}
