package org.example.services;

import org.example.entities.Project;
import org.example.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.example.utils.Utils.copyNotNullProperties;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository repository;

    public Project save(Project project) {
        return repository.save(project);
    }

    public Project update(Project source, Project target) {
        if (target != null && source != null) {
            copyNotNullProperties(source, target);
            return save(target);
        } else {
            return null;
        }
    }

    public List<Project> findAll() {
        return repository.findAll();
    }

    public Optional<Project> findById(long id) {
        return repository.findById(id);
    }

    public boolean delete(Project project) {
        if (project != null && findById(project.getId()).isPresent()) {
            repository.delete(project);
            return true;
        } else return false;
    }

    public Optional<Project> findByName(String name) {
        return repository.findByName(name);
    }
}
