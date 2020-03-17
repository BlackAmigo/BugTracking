package org.example.services;

import org.example.entities.Project;
import org.example.repositories.ProjectRepository;
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
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceImplTest {

    private static final String PROJECT_NAME = "First project";
    private static final long PROJECT_ID = 1;

    @Autowired
    private ProjectServiceImpl projectServiceImpl;

    @MockBean
    private ProjectRepository projectRepositoryMock;

    @Test
    public void save() {
        Project project = new Project();
        project.setName(PROJECT_NAME);
        when(projectRepositoryMock.save(any())).thenReturn(project);

        Project returnedProject = projectServiceImpl.save(project);

        verify(projectRepositoryMock, times(1)).save(project);
        verifyNoMoreInteractions(projectRepositoryMock);
        assertEquals(PROJECT_NAME, returnedProject.getName());
    }

    @Test
    public void findAll() {
        List<Project> projects = new ArrayList<Project>() {{
            add(new Project());
            add(new Project());
        }};

        Pageable firstPageWithTenElements = PageRequest.of(0, 10);
        Page<Project> page = new PageImpl<>(projects, firstPageWithTenElements, projects.size());

        when(projectRepositoryMock.findAll(firstPageWithTenElements)).thenReturn(page);

        Page<Project> foundProjects = projectServiceImpl.getAll(firstPageWithTenElements);

        verify(projectRepositoryMock, times(1)).findAll(firstPageWithTenElements);
        verifyNoMoreInteractions(projectRepositoryMock);
        assertEquals(2, foundProjects.getTotalElements());
    }

    @Test
    public void findAllWhenIsProjectNotFound() {
        List<Project> projects = new ArrayList<>();

        Pageable firstPageWithTenElements = PageRequest.of(0, 10);
        Page<Project> page = new PageImpl<>(projects, firstPageWithTenElements, projects.size());

        when(projectRepositoryMock.findAll(firstPageWithTenElements)).thenReturn(page);

        Page<Project> foundProjects = projectServiceImpl.getAll(firstPageWithTenElements);

        verify(projectRepositoryMock, times(1)).findAll(firstPageWithTenElements);
        verifyNoMoreInteractions(projectRepositoryMock);
        assertTrue(foundProjects.isEmpty());
    }

    @Test
    public void findById() {
        Project project = new Project();
        project.setId(PROJECT_ID);
        project.setName(PROJECT_NAME);
        when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(Optional.of(project));

        Optional<Project> foundProject = projectServiceImpl.getById(PROJECT_ID);

        verify(projectRepositoryMock, times(1)).findById(PROJECT_ID);
        verifyNoMoreInteractions(projectRepositoryMock);
        assertTrue(foundProject.isPresent());
        assertEquals(project, foundProject.get());
    }

    @Test
    public void findByIdWhenIsProjectNotFound() {
        when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(Optional.empty());

        Optional<Project> foundProject = projectServiceImpl.getById(PROJECT_ID);

        verify(projectRepositoryMock, times(1)).findById(PROJECT_ID);
        verifyNoMoreInteractions(projectRepositoryMock);
        assertFalse(foundProject.isPresent());
    }

    @Test
    public void delete() {
        Project project = new Project();
        when(projectRepositoryMock.findById(any())).thenReturn(Optional.of(project));

        assertTrue(projectServiceImpl.delete(project));
        verify(projectRepositoryMock, times(1)).findById(any());
        verify(projectRepositoryMock, times(1)).delete(project);
        verifyNoMoreInteractions(projectRepositoryMock);
    }

    @Test
    public void deleteWhenProjectIsNull() {
        assertFalse(projectServiceImpl.delete(null));
        verify(projectRepositoryMock, times(0)).delete(any());
        verifyNoMoreInteractions(projectRepositoryMock);
    }

    @Test
    public void findByName() {
        Project project = new Project();
        project.setName(PROJECT_NAME);
        when(projectRepositoryMock.findByName(project.getName())).thenReturn(Optional.of(project));

        Optional<Project> foundProject = projectServiceImpl.findByName(PROJECT_NAME);

        verify(projectRepositoryMock, times(1)).findByName(PROJECT_NAME);
        verifyNoMoreInteractions(projectRepositoryMock);
        assertTrue(foundProject.isPresent());
        assertEquals(project, foundProject.get());
    }

    @Test
    public void findByNameWhenIsProjectNotFound() {
        when(projectRepositoryMock.findByName(any())).thenReturn(Optional.empty());

        Optional<Project> foundProject = projectServiceImpl.findByName(any());

        verify(projectRepositoryMock, times(1)).findByName(any());
        verifyNoMoreInteractions(projectRepositoryMock);
        assertFalse(foundProject.isPresent());
    }

    @Test
    public void update() {
        Project projectSource = new Project();
        projectSource.setName("projectSource");
        projectSource.setTextDescription("textDescriptionSource");
        Project projectTarget = new Project();
        projectTarget.setName("projectTarget");
        projectTarget.setTextDescription("textDescriptionTarget");

        copyNotNullNotSameProperties(projectSource, projectTarget);

        when(projectRepositoryMock.save(projectTarget)).thenReturn(projectTarget);

        ValidationResult<Project> validationResult = projectServiceImpl.update(projectSource, projectTarget);
        Project editedProject = validationResult.getEntity();
        Map<String, String> errors = validationResult.getErrors();

        verify(projectRepositoryMock, times(1)).save(projectTarget);
        verifyNoMoreInteractions(projectRepositoryMock);
        assertEquals(editedProject.getName(), projectSource.getName());
        assertEquals(editedProject.getTextDescription(), projectSource.getTextDescription());
        assertTrue(errors.isEmpty());
    }

    @Test
    public void updateWhenParameterIsNull() {
        Project projectSource = new Project();

        ValidationResult<Project> validationResult = projectServiceImpl.update(projectSource, null);
        Project editedProject = validationResult.getEntity();
        Map<String, String> errors = validationResult.getErrors();

        verify(projectRepositoryMock, times(0)).save(any());
        verifyNoMoreInteractions(projectRepositoryMock);
        assertNull(editedProject);
        assertFalse(errors.isEmpty());
    }

    @Test
    public void updateWhenProjectIsNotValid() {
        Project projectSource = new Project();
        projectSource.setLastModifiedDate(new Date());
        Project projectTarget = new Project();

        copyNotNullNotSameProperties(projectSource, projectTarget);

        when(projectRepositoryMock.save(projectTarget)).thenReturn(projectTarget);

        ValidationResult<Project> validationResult = projectServiceImpl.update(projectSource, projectTarget);
        Project editedProject = validationResult.getEntity();
        Map<String, String> errors = validationResult.getErrors();

        verify(projectRepositoryMock, times(0)).save(projectTarget);
        verifyNoMoreInteractions(projectRepositoryMock);
        assertEquals(editedProject.getLastModifiedDate(), projectSource.getLastModifiedDate());
        assertFalse(errors.isEmpty());
    }
}