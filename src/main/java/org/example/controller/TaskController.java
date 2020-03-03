package org.example.controller;

import org.example.entities.Task;
import org.example.entities.TaskStatus;
import org.example.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class TaskController {

    private Date date = new Date();

    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/task")
    public List<Task> getAll() {
        return taskService.getAll();
    }

    @PostMapping(value = "/task")
    public void addTask(@RequestParam(value = "projectId", defaultValue = "1") long projectId,
                        @RequestParam(value = "name", defaultValue = "name") String name,
                        @RequestParam(value = "textDescription", defaultValue = "textDescription") String textDescription,
                        @RequestParam(value = "priority", defaultValue = "10") int priority) {

        Task task = new Task();
        task.setProjectId(projectId);
        task.setName(name);
        task.setTextDescription(textDescription);
        task.setPriority(priority);
        task.setCreatedDate(date);
        task.setLastModifiedDate(date);
        task.setStatus(TaskStatus.NEW);
        taskService.save(task);
    }

    @GetMapping(value = "/task/{id}")
    public Task getTask(@PathVariable("id") long id) {
        Task task = taskService.get(id);
        task.setTextDescription("изменненный текст");
        return task;
    }

    @DeleteMapping(value = "/task/{id}")
    public void deleteTask(@PathVariable("id") long id) {
        taskService.deleteById(id);
    }

}
