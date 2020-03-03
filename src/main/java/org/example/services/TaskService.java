package org.example.services;

import org.example.entities.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    void save(Task task);
    List<Task> getAll();
    Task get(long id);
    void delete(Task task);
    void deleteById(long id);
}
