package org.example.repositories;

import org.example.entities.Task;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
    Optional<Task> findByName(String name);
}
