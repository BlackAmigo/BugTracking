package org.example.services;

import org.example.entities.Project;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {

    void save(Project project);
    List<Project> getAll();
    Project get(long id);
    void delete(Project project);
    void deleteById(long id);
}
