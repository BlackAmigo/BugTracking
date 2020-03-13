package org.example.controller;

import org.example.entities.Task;
import org.example.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "api/tasks")
public class TaskController extends AbstractController<TaskService>{

    @Autowired
    private TaskService taskService;

    @GetMapping("/name/{name}")
    public ResponseEntity<Object> findByName (@PathVariable("name") String name){
        Optional<Task> project = taskService.findByName(name);
        if (project.isPresent()) {
            return new ResponseEntity<>(project, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
