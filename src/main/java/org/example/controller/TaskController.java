package org.example.controller;

import org.example.entities.Task;
import org.example.entities.TaskStatus;
import org.example.services.ValidationResult;
import org.example.services.TaskService;
import org.example.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/tasks")
public class TaskController extends AbstractController<Task> {

    @Autowired
    private TaskService taskService;

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Task target, Task source) {
        if (target.getStatus() == TaskStatus.CLOSED) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
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
