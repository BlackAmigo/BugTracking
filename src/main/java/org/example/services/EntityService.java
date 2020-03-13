package org.example.services;

import java.util.List;
import java.util.Optional;

public interface EntityService<E> {
    E save(E entity);
    E update(E source, E target);
    List<E> getAll();
    Optional<E> getById(Long id);
    boolean delete(E entity);
}
