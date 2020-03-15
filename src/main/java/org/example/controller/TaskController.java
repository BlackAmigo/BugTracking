package org.example.controller;

import org.example.entities.Task;
import org.example.entities.TaskStatus;
import org.example.services.TaskService;
import org.example.services.ValidationResult;
import org.example.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "api/tasks")
public class TaskController extends AbstractController<Task> {

    @Autowired
    private TaskService taskService;

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Task target, Task source) {
        if (target.getStatus() == TaskStatus.CLOSED) {
            Map<String, String> map = new HashMap<String, String>(){{
                put("status", "The task is " + target.getStatus() + " and cannot be modified");
            }};
            return new ResponseEntity<>(Utils.getResultErrors(target, map),HttpStatus.BAD_REQUEST);
        } else {
            ValidationResult<Task> validationResult = taskService.update(source, target);
            if (validationResult.getErrors().isEmpty()) {
                return new ResponseEntity<>(validationResult.getEntity(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Utils.getResultErrors(validationResult.getEntity(), validationResult.getErrors()), HttpStatus.BAD_REQUEST);
            }
        }
    }
}
