package org.example.services;

import org.example.entities.Project;
import org.example.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository repository;

    public void save(Project project) {
        repository.save(project);
    }

    public List<Project> getAll() {
        return repository.findAll();
    }

    public Project get(long id) {
        return repository.getOne(id);
    }

    public void delete(Project project) {
        repository.delete(project);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
