//package org.example.services;
//
//import org.example.entities.Project;
//import org.example.entities.Task;
//import org.example.repositories.TaskRepository;
//import org.example.utils.Utils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class TaskServiceImpl implements TaskService {
//
//    @Autowired
//    private TaskRepository repository;
//
//    @Override
//    public Task save(Task task) {
//        return repository.save(task);
//    }
//
//    @Override
//    public List<Task> getAll() {
//        return repository.findAll();
//    }
//
//    @Override
//    public Optional<Task> get(Long id) {
//        return repository.findById(id);
//    }
//
//    @Override
//    public Task update(Task source, Task target) {
//            Optional<Task> project = repository.findById(source.getId());
//            if (project.isPresent()) {
//                Utils.copyNotNullProperties(source, target);
//                return target;
//            } else return null;
//    }
//
//    @Override
//    public void delete(Task task) {
//        repository.delete(task);
//    }
//}
