package org.example.controller;

import org.example.entities.Project;
import org.example.entities.Task;
import org.example.entities.TaskStatus;
import org.example.services.ProjectService;
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
import java.util.Map;

import static org.example.utils.DateUtils.parseDate;

@RestController
@RequestMapping(value = "api/projects")
public class ProjectController extends AbstractController<Project> {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Project target, Project source) {
        ValidationResult<Project> validationResult = projectService.update(source, target);
        if (validationResult.getErrors().isEmpty()) {
            return new ResponseEntity<>(validationResult.getEntity(), HttpStatus.OK);
        } else {
            Map<String, Object> map = Utils.getResultErrors(validationResult.getEntity(), validationResult.getErrors());
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<Page<Task>> getAllProjectTasks(@PathVariable("id") long id, Pageable pageable) {
        Page<Task> page = taskService.findAllByProjectId(id, pageable);
        if (page.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{id}/tasks/filter/between_date")
    public ResponseEntity<Page<Task>> filterTasksByProjectIdBetweenDate(@PathVariable("id") Long id,
                                                                        String dateStart,
                                                                        String dateEnd,
                                                                        Pageable pageable) throws ParseException {
        Date dateStartParsed = parseDate(dateStart);
        Date dateEndParsed = parseDate(dateEnd);
        Page<Task> tasks = taskService.findAllByProjectIdAndCreatedDateBetween(id, dateStartParsed, dateEndParsed, pageable);
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}/tasks/filter/status")
    public ResponseEntity<Page<Task>> filterTasksByProjectIdAndStatus(@PathVariable("id") Long id,
                                                                      TaskStatus status,
                                                                      Pageable pageable) {
        Page<Task> tasks = taskService.findAllByProjectIdAndStatus(id, status, pageable);
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}/tasks/filter/priority")
    public ResponseEntity<Page<Task>> findAllByProjectIdAndByPriority(@PathVariable("id") Long id,
                                                                      int priority,
                                                                      Pageable pageable) {
        Page<Task> tasks = taskService.findAllByProjectIdAndByPriority(id, priority, pageable);
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }
    }
}
