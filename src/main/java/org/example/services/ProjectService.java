package org.example.services;

import org.example.entities.Project;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProjectService {

    Project save(Project project);
    List<Project> getAll();
    Optional<Project> get(Long id);
    void delete(Project project);
}
