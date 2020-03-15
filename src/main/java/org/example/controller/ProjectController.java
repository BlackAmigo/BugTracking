package org.example.controller;

import org.example.entities.Project;
import org.example.services.ProjectService;
import org.example.services.ValidationResult;
import org.example.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "api/projects")
public class ProjectController extends AbstractController<Project> {

    @Autowired
    private ProjectService projectService;

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
}
