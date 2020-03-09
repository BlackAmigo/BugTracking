package org.example.controller;

import org.example.entities.Project;
import org.example.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.example.utils.Utils.copyNotNullProperties;
import static org.example.utils.Utils.getBindingResultErrors;

@RestController
@RequestMapping(value = "/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        if (!projectService.findAll().isEmpty()) {
            return new ResponseEntity<>(projectService.findAll(), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getProjectById(@PathVariable("id") long id) {
        Optional<Project> project = projectService.findById(id);
        if (project.isPresent()) {
            return new ResponseEntity<>(project, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addProject(@Valid Project project, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(getBindingResultErrors(project, bindingResult), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(projectService.save(project), HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable("id") Project target, Project source) {
        Project updatedProject = projectService.update(source, target);
        if (updatedProject != null) {
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteProject(@PathVariable("id") Project project) {
        boolean result = projectService.delete(project);
        if (result)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
