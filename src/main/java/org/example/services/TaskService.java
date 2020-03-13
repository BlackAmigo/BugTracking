package org.example.services;

import org.example.entities.Task;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TaskService extends EntityService<Task>{
    Optional<Task> findByName(String name);
}
