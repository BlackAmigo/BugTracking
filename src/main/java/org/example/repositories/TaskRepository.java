package org.example.repositories;

import org.example.entities.Task;
import org.example.entities.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
    Optional<Task> findByName(String name);

    Page<Task> findAllByProjectIdAndCreatedDateBetween(Long id, Date creationDateStart, Date creationDateEnd, Pageable pageable);
    Page<Task> findAllByProjectId (Long id, Pageable pageable);
    Page<Task> findAllByProjectIdAndStatus (Long id, TaskStatus status, Pageable pageable);
    Page<Task> findAllByProjectIdAndPriority (Long id, int priority, Pageable pageable);

    Page<Task> findAllByCreatedDateBetween(Date creationDateStart, Date creationDateEnd, Pageable pageable);
    Page<Task> findAllByStatus (TaskStatus status, Pageable pageable);
    Page<Task> findAllByPriority (int priority, Pageable pageable);
}