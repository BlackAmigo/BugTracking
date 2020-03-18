package org.example.services;

import org.example.entities.Project;
import org.example.entities.Task;
import org.example.repositories.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.example.utils.Utils.copyNotNullNotSameProperties;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceImplTest {

    private static final String TASK_NAME = "First task";
    private static final long TASK_ID = 1;

    @Autowired
    private TaskServiceImpl taskServiceImpl;

    @MockBean
    private TaskRepository taskRepositoryMock;

    @Test
    public void save() {
        Task task = new Task();
        task.setName(TASK_NAME);
        when(taskRepositoryMock.save(any())).thenReturn(task);

        Task returnedTask = taskServiceImpl.save(task);

        verify(taskRepositoryMock, times(1)).save(task);
        verifyNoMoreInteractions(taskRepositoryMock);
        assertEquals(TASK_NAME, returnedTask.getName());
    }

    @Test
    public void getAll() {
        List<Task> taskList = new ArrayList<Task>() {{
            add(new Task());
            add(new Task());
        }};

        Pageable firstPageWithTenElements = PageRequest.of(0, 10);
        Page<Task> page = new PageImpl<>(taskList, firstPageWithTenElements, taskList.size());

        when(taskRepositoryMock.findAll(firstPageWithTenElements)).thenReturn(page);

        Page<Task> foundTask = taskServiceImpl.getAll(firstPageWithTenElements);

        verify(taskRepositoryMock, times(1)).findAll(firstPageWithTenElements);
        verifyNoMoreInteractions(taskRepositoryMock);
        assertEquals(2, foundTask.getTotalElements());
    }

    @Test
    public void getAllWhenIsTaskNotFound() {
        List<Task> taskList = new ArrayList<>();

        Pageable firstPageWithTenElements = PageRequest.of(0, 10);
        Page<Task> page = new PageImpl<>(taskList, firstPageWithTenElements, taskList.size());

        when(taskRepositoryMock.findAll(firstPageWithTenElements)).thenReturn(page);

        Page<Task> foundTask = taskServiceImpl.getAll(firstPageWithTenElements);

        verify(taskRepositoryMock, times(1)).findAll(firstPageWithTenElements);
        verifyNoMoreInteractions(taskRepositoryMock);
        assertTrue(foundTask.isEmpty());
    }

    @Test
    public void getById() {
        Task task = new Task();
        task.setId(TASK_ID);
        task.setName(TASK_NAME);
        when(taskRepositoryMock.findById(TASK_ID)).thenReturn(Optional.of(task));

        Optional<Task> foundTask = taskServiceImpl.getById(TASK_ID);

        verify(taskRepositoryMock, times(1)).findById(TASK_ID);
        verifyNoMoreInteractions(taskRepositoryMock);
        assertTrue(foundTask.isPresent());
        assertEquals(task, foundTask.get());
    }

    @Test
    public void getByIdWhenIsTaskNotFound() {
        when(taskRepositoryMock.findById(TASK_ID)).thenReturn(Optional.empty());

        Optional<Task> foundTask = taskServiceImpl.getById(TASK_ID);

        verify(taskRepositoryMock, times(1)).findById(TASK_ID);
        verifyNoMoreInteractions(taskRepositoryMock);
        assertFalse(foundTask.isPresent());
    }

    @Test
    public void delete() {
        Task task = new Task();
        when(taskRepositoryMock.findById(any())).thenReturn(Optional.of(task));

        assertTrue(taskServiceImpl.delete(task));
        verify(taskRepositoryMock, times(1)).findById(any());
        verify(taskRepositoryMock, times(1)).delete(task);
        verifyNoMoreInteractions(taskRepositoryMock);
    }

    @Test
    public void deleteWhenTaskIsNull() {
        assertFalse(taskServiceImpl.delete(null));
        verify(taskRepositoryMock, times(0)).delete(any());
        verifyNoMoreInteractions(taskRepositoryMock);
    }

    @Test
    public void findByName() {
        Task task = new Task();
        task.setName(TASK_NAME);
        when(taskRepositoryMock.findByName(task.getName())).thenReturn(Optional.of(task));

        Optional<Task> foundTask = taskServiceImpl.findByName(TASK_NAME);

        verify(taskRepositoryMock, times(1)).findByName(TASK_NAME);
        verifyNoMoreInteractions(taskRepositoryMock);
        assertTrue(foundTask.isPresent());
        assertEquals(task, foundTask.get());
    }

    @Test
    public void findByNameWhenIsTaskNotFound() {
        when(taskRepositoryMock.findByName(any())).thenReturn(Optional.empty());

        Optional<Task> foundTask = taskServiceImpl.findByName(any());

        verify(taskRepositoryMock, times(1)).findByName(any());
        verifyNoMoreInteractions(taskRepositoryMock);
        assertFalse(foundTask.isPresent());
    }

    @Test
    public void update() {
        Project project = new Project();
        project.setName("Project");
        project.setTextDescription("Description");

        Task taskSource = new Task();
        taskSource.setName("taskSource");
        taskSource.setPriority(1);
        taskSource.setProject(project);

        Task taskTarget = new Task();
        taskTarget.setName("taskTarget");
        taskTarget.setTextDescription("textDescriptionTarget");

        copyNotNullNotSameProperties(taskSource, taskTarget);

        when(taskRepositoryMock.save(taskTarget)).thenReturn(taskTarget);

        ValidationResult<Task> validationResult = taskServiceImpl.update(taskSource, taskTarget);
        Task editedTask = validationResult.getEntity();
        Map<String, String> errors = validationResult.getErrors();

        verify(taskRepositoryMock, times(1)).save(taskTarget);
        verifyNoMoreInteractions(taskRepositoryMock);
        assertEquals(editedTask.getName(), taskSource.getName());
        assertEquals(editedTask.getPriority(), taskSource.getPriority());
        assertTrue(errors.isEmpty());
    }

    @Test
    public void updateWhenParameterIsNull() {
        Task taskSource = new Task();

        ValidationResult<Task> validationResult = taskServiceImpl.update(taskSource, null);
        Task editedTask = validationResult.getEntity();
        Map<String, String> errors = validationResult.getErrors();

        verify(taskRepositoryMock, times(0)).save(any());
        verifyNoMoreInteractions(taskRepositoryMock);
        assertNull(editedTask);
        assertFalse(errors.isEmpty());
    }

    @Test
    public void updateWhenTaskIsNotValid() {
        Task taskSource = new Task();
        taskSource.setName(TASK_NAME);
        taskSource.setPriority(-1);
        taskSource.setLastModifiedDate(new Date());
        Task taskTarget = new Task();

        copyNotNullNotSameProperties(taskSource, taskTarget);

        when(taskRepositoryMock.save(taskTarget)).thenReturn(taskTarget);

        ValidationResult<Task> validationResult = taskServiceImpl.update(taskSource, taskTarget);
        Task editedTask = validationResult.getEntity();
        Map<String, String> errors = validationResult.getErrors();

        verify(taskRepositoryMock, times(0)).save(taskTarget);
        verifyNoMoreInteractions(taskRepositoryMock);
        assertEquals(editedTask.getLastModifiedDate(), taskSource.getLastModifiedDate());
        assertFalse(errors.isEmpty());
    }
}