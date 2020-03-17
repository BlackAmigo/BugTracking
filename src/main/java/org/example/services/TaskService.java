package org.example.services;

import org.example.entities.Task;
import org.example.entities.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface TaskService extends EntityService<Task>{
    Page<Task> findAllByProjectIdAndCreatedDateBetween(Long projectId, Date dateStart, Date dateEnd, Pageable pageable);
    Page<Task> findAllByProjectId (Long id, Pageable pageable);
    Page<Task> findAllByProjectIdAndStatus (Long projectId, TaskStatus status, Pageable pageable);
    Page<Task> findAllByProjectIdAndByPriority (Long projectId,int priority, Pageable pageable);

    Page<Task> findAllByCreatedDateBetween(Date dateStart, Date dateEnd, Pageable pageable);
    Page<Task> findAllByStatus (TaskStatus status, Pageable pageable);
    Page<Task> findAllByPriority (int priority, Pageable pageable);
}
