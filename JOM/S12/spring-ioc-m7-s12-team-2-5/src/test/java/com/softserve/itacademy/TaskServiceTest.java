package com.softserve.itacademy;

import com.softserve.itacademy.Exceptions.UserNotFoundException;
import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;
import com.softserve.itacademy.service.impl.TaskServiceImpl;
import com.softserve.itacademy.service.impl.ToDoServiceImpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;

@RunWith(JUnitPlatform.class)
public class TaskServiceTest {
    private static UserService userService;

    @BeforeAll
    public static void setupBeforeClass() throws Exception {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        annotationConfigContext.close();
    }

    @Test
    public void testAddMethodWithExistAndNonExistTask() throws UserNotFoundException {
        User user = new User("Sakurada", "Kanade", "dandelion@mail.ru", "pass2021word", new ArrayList<>());
        userService.addUser(user);
        
        List<Task> taskList = new ArrayList<Task>();
        taskList.add(new Task("watch anime Akira", Priority.HIGH));
        taskList.add(new Task("watch anime Azur Lane", Priority.LOW));
        
        ToDo toDo = new ToDo("Anime to watch list",  LocalDateTime.now(), user, taskList);
        
        ToDoService toDoService = new ToDoServiceImpl(userService);
        TaskService taskService = new TaskServiceImpl(toDoService);      
        
        toDoService.addTodo(toDo, user);        
        
        Task task = new Task("watch anime Akaneiro ni Somaru Saka", Priority.HIGH);        
        taskService.addTask(task, toDo);
        
        Assert.assertTrue(taskService.getAll().contains(task));
        
        Task taskDuplicate = new Task("watch anime Azur Lane", Priority.LOW);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> taskService.addTask(taskDuplicate, toDo));
    }
    
    @Test
    public void testUpdateTaskMethodWithExistAndNonExistTask() throws UserNotFoundException {
        User user = new User("Nomura", "Kasumi", "kasa@mail.ru", "MyPass2021wOrd", new ArrayList<>());
        userService.addUser(user);
        
        List<Task> taskList = new ArrayList<Task>();
        taskList.add(new Task("read Asobi Asobase", Priority.HIGH));
        taskList.add(new Task("read Hatsu Inu", Priority.LOW));
        
        ToDo toDo = new ToDo("Read manga list",  LocalDateTime.now(), user, taskList);
        
        ToDoService toDoService = new ToDoServiceImpl(userService);
        TaskService taskService = new TaskServiceImpl(toDoService);      
        
        toDoService.addTodo(toDo, user);        
        
        taskService.updateTask(new Task("read Asobi Asobase", Priority.LOW));
        
        Assert.assertEquals(
                Priority.LOW,
                taskService.getByToDoName(toDo, "read Asobi Asobase").getPriority());
        
        Task taskNotExist = new Task("read nothing", Priority.HIGH); 
                
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> taskService.updateTask(taskNotExist));
    }
   
    @Test
    public void testDeleteMethod() throws UserNotFoundException {
        User user = new User("Edward", "Screven", "oracle@gmail.com", "noPass", new ArrayList<>());
        userService.addUser(user);
        
        List<Task> taskList = new ArrayList<Task>();
        taskList.add(new Task("learn Java", Priority.HIGH));
        taskList.add(new Task("learn C++", Priority.LOW));
        List<Task> taskList2 = new ArrayList<Task>();
        taskList2.add(new Task("practice Java", Priority.HIGH));
        taskList2.add(new Task("learn C++", Priority.LOW));
        
        ToDo toDo = new ToDo("Learn languages list",  LocalDateTime.now(), user, taskList);
        ToDo toDo2 = new ToDo("Practice languages list",  LocalDateTime.now(), user, taskList2);
        
        ToDoService toDoService = new ToDoServiceImpl(userService);
        TaskService taskService = new TaskServiceImpl(toDoService);
        
        toDoService.addTodo(toDo, user);  
        toDoService.addTodo(toDo2, user);  
        
        int beforeDelete = taskService.getAll().size();
        
        taskService.deleteTask(new Task("learn C++", Priority.LOW));
        
        int afterDelete = taskService.getAll().size();
        
        Assert.assertEquals(beforeDelete - 2, afterDelete);
        
        beforeDelete = afterDelete;                
        taskService.deleteTask(new Task("learn Ruby", Priority.HIGH));
        afterDelete = taskService.getAll().size();
        
        Assert.assertEquals(beforeDelete, afterDelete);
    }
    
    
    @Test
    public void testGetAllMethod() throws UserNotFoundException {
        ToDoService toDoService = new ToDoServiceImpl(userService);
        TaskService taskService = new TaskServiceImpl(toDoService);  
        
        for (Task tempTask : taskService.getAll()) {
            taskService.deleteTask(tempTask);
        }
        
        Assert.assertEquals(0, taskService.getAll().size());
        
        User user = new User("Obama", "Ivan", "obama@mail.ru", "amEriCa", new ArrayList<>());
        userService.addUser(user);
        
        List<Task> taskList = new ArrayList<Task>();
        taskList.add(new Task("sing Song", Priority.HIGH));
        taskList.add(new Task("read letter", Priority.LOW));
        
        ToDo toDo = new ToDo("some actions",  LocalDateTime.now(), user, taskList);
                
        toDoService.addTodo(toDo, user);        
        
        Assert.assertEquals(2, taskService.getAll().size());        
    }
    
    @Test
    public void testGetByToDo() {
        User user = new User("Dart", "Vaider", "star@wars.eu", "deathStar", new ArrayList<>());
        userService.addUser(user);
        
        List<Task> taskList = new ArrayList<Task>();
        taskList.add(new Task("practice lightsaber", Priority.HIGH));
        taskList.add(new Task("practice force", Priority.LOW));
        taskList.add(new Task("watch My Little Pony", Priority.LOW));
        
        ToDo toDo = new ToDo("My Power list",  LocalDateTime.now(), user, taskList);
        
        ToDoService toDoService = new ToDoServiceImpl(userService);
        TaskService taskService = new TaskServiceImpl(toDoService);      
        
        List<Task> listCheck = taskService.getByToDo(toDo);
        Assert.assertEquals(3, listCheck.size());
        Assert.assertEquals(
                true, 
                listCheck.contains(
                        new Task("watch My Little Pony", Priority.LOW)
                ));
    }
    
    @Test
    public void testGetByToDoName() throws UserNotFoundException {
        User user = new User("Marui", "Mitsuba", "itsuba@mail.ru", "Maru2021Mitsuba", new ArrayList<>());
        userService.addUser(user);
        
        List<Task> taskList = new ArrayList<Task>();
        taskList.add(new Task("feed cat", Priority.HIGH));
        taskList.add(new Task("feed dog", Priority.LOW));
        
        ToDo toDo = new ToDo("household chores",  LocalDateTime.now(), user, taskList);
        
        ToDoService toDoService = new ToDoServiceImpl(userService);
        TaskService taskService = new TaskServiceImpl(toDoService);      
        
        toDoService.addTodo(toDo, user);  
        
        Assert.assertEquals(
                new Task("feed dog", Priority.LOW),
                taskService.getByToDoName(toDo, "feed dog"));
                
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> taskService.getByToDoName(toDo, "feed mouse"));
    }  
    
    @Test
    public void testGetByUserName() throws UserNotFoundException {
        User user = new User("Yoshioka", "Yuki", "yuka@mail.ru", "Yoshioka789Yuki", new ArrayList<>());
        userService.addUser(user);
        
        List<Task> taskList = new ArrayList<Task>();
        taskList.add(new Task("run tests", Priority.HIGH));
        taskList.add(new Task("water plant", Priority.LOW));
        
        ToDo toDo = new ToDo("different acts",  LocalDateTime.now(), user, taskList);
        
        ToDoService toDoService = new ToDoServiceImpl(userService);
        TaskService taskService = new TaskServiceImpl(toDoService);      
        
        toDoService.addTodo(toDo, user);
    
        Assert.assertEquals(
                new Task("run tests", Priority.HIGH),
                taskService.getByUserName(user, "run tests"));
                
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> taskService.getByUserName(user, "run plant"));
        
    }
}
