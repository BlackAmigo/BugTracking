package org.example.services;

import org.example.entities.Project;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ProjectService extends EntityService<Project>{
    Optional<Project> findByName(String name);
}
