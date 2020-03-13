package org.example.services;

import org.example.entities.Project;
import org.example.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.example.utils.Utils.copyNotNullProperties;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectRepository repository;

    @Override
    public Project save(Project project) {
        project.setLastModifiedDate(new Date());
        return repository.save(project);
    }

    @Override
    public Project update(Project source, Project target) {
        if (target != null && source != null) {
            copyNotNullProperties(source, target);
            return save(target);
        } else {
            return null;
        }
    }

    @Override
    public List<Project> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Project> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public boolean delete(Project project) {
        if (project != null && getById(project.getId()).isPresent()) {
            repository.delete(project);
            return true;
        } else return false;
    }

    @Override
    public Optional<Project> findByName(String name) {
        return repository.findByName(name);
    }
}

