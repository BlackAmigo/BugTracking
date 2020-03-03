package org.example.controller;

import org.example.entities.Project;
import org.example.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping(value = "/project")
    public List<Project> getAll(){
        return projectService.getAll();
    }

    @PostMapping(value = "/project")
    public void addProject(@RequestParam (value = "name", defaultValue = "Имя") String name,
                           @RequestParam (value = "textDescription", defaultValue = "описание") String textDescription){
        Project project = new Project();
        project.setName(name);
        project.setTextDescription(textDescription);
        projectService.save(project);
    }

    @GetMapping(value = "/project/{id}")
    public Project getProject(@PathVariable ("id") long id){
        Project project = projectService.get(id);
        project.setTextDescription("изменненный текст");
        return project;
    }

    @DeleteMapping(value = "/project/{id}")
    public void deleteProject(@PathVariable ("id") long id){
        projectService.deleteById(id);
    }
}
