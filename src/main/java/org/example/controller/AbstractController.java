package org.example.controller;

import org.example.services.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

import static org.example.utils.Utils.getBindingResultErrors;

public abstract class AbstractController<E>{

    @Autowired
    private EntityService<E> service;

    @GetMapping
    public ResponseEntity<Page<E>> getAll(Pageable pageable) {
        Page<E> page = service.getAll(pageable);
        if (page.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else
            return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        Optional<E> entity = service.getById(id);
        if (entity.isPresent()) {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid E entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(getBindingResultErrors(entity, bindingResult), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(service.save(entity), HttpStatus.OK);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Object> findByName (@PathVariable("name") String name){
        Optional<E> project = service.findByName(name);
        if (project.isPresent()) {
            return new ResponseEntity<>(project, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") E entity) {
        boolean result = service.delete(entity);
        if (result)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
