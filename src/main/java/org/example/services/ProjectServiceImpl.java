package org.example.services;

import org.example.entities.Project;
import org.example.repositories.ProjectRepository;
import org.example.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository repository;

    @Override
    public Project save(Project project) {
        return repository.save(project);
    }

    @Override
    public List<Project> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Project> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Project project) {
        repository.delete(project);
    }
}
