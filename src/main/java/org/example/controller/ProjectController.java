package org.example.controller;

import org.example.entities.Project;
import org.example.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "api/projects")
public class ProjectController extends AbstractController<Project>{

    @Autowired
    private ProjectService projectService;

    @GetMapping("/name/{name}")
    public ResponseEntity<Object> findByName (@PathVariable("name") String name){
        Optional<Project> project = projectService.findByName(name);
        if (project.isPresent()) {
            return new ResponseEntity<>(project, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
