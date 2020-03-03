package org.example.services;

import org.example.entities.Task;
import org.example.repositories.TaskRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    public void save(Task task) {
        repository.save(task);
    }

    public List<Task> getAll() {
        return repository.findAll();
    }

    public Task get(long id) {
        return repository.getOne(id);
    }

    public void delete(Task task) {
        repository.delete(task);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
