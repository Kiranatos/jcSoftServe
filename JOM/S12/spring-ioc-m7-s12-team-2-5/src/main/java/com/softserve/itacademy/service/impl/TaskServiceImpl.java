package com.softserve.itacademy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class TaskServiceImpl implements TaskService {

    private ToDoService toDoService;

    @Autowired
    public TaskServiceImpl(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    /**
     * Add new Task, that not exist in ToDo container.
     * 
     * @param task new Task;
     * @param todo existed ToDo container;
     * @return added task;
     * @throws IllegalArgumentException if ToDo container not exist in Service or Task already exist in ToDo container.
     */
    @Override
    public Task addTask(Task task, ToDo todo) throws IllegalArgumentException {
        boolean statusToDo = toDoService.getAll().stream().anyMatch(todo2 -> todo2.getTitle().equals(todo.getTitle()));
        if (statusToDo) {
            boolean statusTask = todo.getTasks().stream().anyMatch(task2 -> task2.getName().equals(task.getName()));
            if (!statusTask) {
                todo.getTasks().add(task);
                return task;
            }
            throw new IllegalArgumentException("Task " + task.toString() + " already exist in ToDo conatainer!");
        }
        throw new IllegalArgumentException("ToDo conatainer " + todo.toString() + " not found in Service!");        
    }

    /**
     * Update task priority.
     * 
     * @param task existed task
     * @return updated task
     * @throws IllegalArgumentException if task not found in ToDo conatainer.
     */
    @Override
    public Task updateTask(Task task) {
        for (Task tempTask : getAll()) {
            if (Objects.equals(tempTask.getName(), task.getName())) {
                tempTask.setPriority(task.getPriority());
                return tempTask;
            }
        }
        throw new IllegalArgumentException("Task " + task.getName() + " not found in ToDo conatainer!");
    }

    /**
     * Delete all tasks in all ToDo containers in all users.
     * 
     * @param task task that need to delete everywhere
     */
    @Override
    public void deleteTask(Task task) {
        for (ToDo td : toDoService.getAll()) {
            td.getTasks().remove(task);
        }
    }

    /**
     * Get all tasks from all ToDo containers from all users.
     * 
     * @return list of tasks
     */
    @Override
    public List<Task> getAll() {
        List<Task> list = new ArrayList<>();
        for (ToDo toDo : toDoService.getAll()) {
            list.addAll(toDo.getTasks());
        }
        return list;
    }

    /**
     * Get all tasks from certain ToDo container.
     * 
     * @param todo ToDo container with tasks
     * @return list of tasks
     */
    @Override
    public List<Task> getByToDo(ToDo todo) {
        return todo.getTasks();
    }

    /**
     * Find task by name in certain ToDo container.
     * 
     * @param todo ToDo container with tasks
     * @param name task's name
     * @return found task
     * @throws IllegalArgumentException if task not found in ToDo conatainer.
     */
    @Override
    public Task getByToDoName(ToDo todo, String name) {
        for (Task task : todo.getTasks()) {
            if (Objects.equals(task.getName(), name)) {
                return task;
            }
        }
        throw new IllegalArgumentException("Task with " + name + " not found in ToDo conatainer!");
    }
    
    /**
     * Find task by name in certain User.
     * 
     * @param user certain User;
     * @param name task's name;
     * @return found task;
     * @throws IllegalArgumentException if task not found in ToDo conatainer.
     */
    @Override
    public Task getByUserName(User user, String name) {
        for (ToDo todo : user.getMyTodos()) {
            for (Task task : todo.getTasks()) {
                if (task.getName().equals(name)) {
                    return task;
                }
            }
        }
        String message = String.format("Task with %s not found in user %s %s!", 
                name, user.getFirstName(), user.getLastName());
        throw new IllegalArgumentException(message);
    }
}
