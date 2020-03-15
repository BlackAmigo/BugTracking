package org.example.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EntityService<E> {
    E save(E entity);
    ValidationResult<E> update(E source, E target);
    Page<E> getAll(Pageable pageable);
    Optional<E> getById(Long id);
    boolean delete(E entity);
    Optional<E> findByName(String name);
}
