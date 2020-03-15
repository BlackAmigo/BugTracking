package org.example.services;

import org.example.entities.Project;
import org.example.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static org.example.utils.Utils.copyProperties;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository repository;

    @Override
    public Project save(Project project) {
        project.setLastModifiedDate(new Date());
        return repository.save(project);
    }

    @Override
    public ValidationResult<Project> update(Project source, Project target) {
        ValidationResult<Project> result = copyProperties(source, target);
        if (result.getErrors().isEmpty()) {
            save(target);
        }
        return new ValidationResult<>(result.getEntity(), result.getErrors());
    }

    @Override
    public Page<Project> getAll(Pageable pageable) {
        return repository.findAll(pageable);
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

