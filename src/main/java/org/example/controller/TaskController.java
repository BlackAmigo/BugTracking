package org.example.controller;

import org.example.entities.Task;
import org.example.entities.TaskStatus;
import org.example.services.TaskService;
import org.example.services.ValidationResult;
import org.example.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.example.utils.DateUtils.parseDate;

@RestController
@RequestMapping(value = "api/tasks")
public class TaskController extends AbstractController<Task> {

    @Autowired
    private TaskService taskService;

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Task target, Task source) {
        if (target.getStatus() == TaskStatus.CLOSED) {
            Map<String, String> map = new HashMap<String, String>() {{
                put("status", "The task is " + target.getStatus() + " and cannot be modified");
            }};
            return new ResponseEntity<>(Utils.getResultErrors(target, map), HttpStatus.BAD_REQUEST);
        } else {
            ValidationResult<Task> validationResult = taskService.update(source, target);
            if (validationResult.getErrors().isEmpty()) {
                return new ResponseEntity<>(validationResult.getEntity(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Utils.getResultErrors(validationResult.getEntity(), validationResult.getErrors()), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @GetMapping("/filter/between_date")
    public ResponseEntity<Page<Task>> filterTasksByCreatedDateBetween(String dateStart,
                                                                      String dateEnd,
                                                                      Pageable pageable) throws ParseException {
        Date dateStartParsed = parseDate(dateStart);
        Date dateEndParsed = parseDate(dateEnd);
        Page<Task> tasks = taskService.findAllByCreatedDateBetween(dateStartParsed, dateEndParsed, pageable);
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }
    }

    @GetMapping("/filter/status")
    public ResponseEntity<Page<Task>> filterTasksByStatus(TaskStatus status,
                                                          Pageable pageable) {
        Page<Task> tasks = taskService.findAllByStatus(status, pageable);
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }
    }

    @GetMapping("/filter/priority")
    public ResponseEntity<Page<Task>> findAllByPriority(int priority,
                                                        Pageable pageable) {
        Page<Task> tasks = taskService.findAllByPriority(priority, pageable);
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }
    }
}
