package org.example.services;

import org.example.entities.Task;
import org.example.entities.TaskStatus;
import org.example.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static org.example.utils.Utils.copyProperties;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository repository;

    @Override
    public Task save(Task task) {
        task.setLastModifiedDate(new Date());
        return repository.save(task);
    }

    @Override
    public ValidationResult<Task> update(Task source, Task target) {
        ValidationResult<Task> result = copyProperties(source, target);
        if (result.getErrors().isEmpty()) {
            save(target);
        }
        return new ValidationResult<>(result.getEntity(), result.getErrors());
    }

    @Override
    public Page<Task> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Task> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public boolean delete(Task task) {
        if (task != null && getById(task.getId()).isPresent()) {
            repository.delete(task);
            return true;
        } else return false;
    }

    @Override
    public Optional<Task> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Page<Task> findAllByProjectIdAndCreatedDateBetween(Long projectId, Date dateStart, Date dateEnd, Pageable pageable) {
        return repository.findAllByProjectIdAndCreatedDateBetween(projectId, dateStart, dateEnd, pageable);
    }

    @Override
    public Page<Task> findAllByProjectId(Long projectId, Pageable pageable) {
        return repository.findAllByProjectId(projectId, pageable);
    }

    @Override
    public Page<Task> findAllByProjectIdAndStatus(Long id, TaskStatus status, Pageable pageable) {
        return repository.findAllByProjectIdAndStatus(id, status, pageable);
    }

    @Override
    public Page<Task> findAllByProjectIdAndByPriority(Long id, int priority, Pageable pageable) {
        return repository.findAllByProjectIdAndPriority(id, priority, pageable);
    }

    @Override
    public Page<Task> findAllByCreatedDateBetween(Date dateStart, Date dateEnd, Pageable pageable) {
        return repository.findAllByCreatedDateBetween(dateStart, dateEnd, pageable);
    }

    @Override
    public Page<Task> findAllByStatus(TaskStatus status, Pageable pageable) {
        return repository.findAllByStatus(status, pageable);
    }

    @Override
    public Page<Task> findAllByPriority(int priority, Pageable pageable) {
        return repository.findAllByPriority(priority, pageable);
    }
}
