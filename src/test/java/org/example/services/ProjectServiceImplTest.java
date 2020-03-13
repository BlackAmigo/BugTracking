//package org.example.services;
//
//import org.example.entities.Project;
//import org.example.repositories.ProjectRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ProjectServiceImplTest {
//
//    private static final String PROJECT_NAME = "First project";
//    private static final long PROJECT_ID = 1;
//
//    @Autowired
//    private ProjectServiceImpl projectServiceImpl;
//
//    @MockBean
//    private ProjectRepository projectRepositoryMock;
//
//    @Test
//    public void save() {
//        Project project = new Project();
//        project.setName(PROJECT_NAME);
//        when(projectRepositoryMock.save(any())).thenReturn(project);
//
//        Project returnedProject = projectServiceImpl.save(project);
//
//        verify(projectRepositoryMock, times(1)).save(project);
//        verifyNoMoreInteractions(projectRepositoryMock);
//        assertEquals(PROJECT_NAME, returnedProject.getName());
//    }
//
//    @Test
//    public void findAll() {
//        List<Project> projects = new ArrayList<Project>() {{
//            add(new Project());
//            add(new Project());
//        }};
//        when(projectRepositoryMock.findAll()).thenReturn(projects);
//
//        List<Project> foundProjects = projectServiceImpl.findAll();
//
//        verify(projectRepositoryMock, times(1)).findAll();
//        verifyNoMoreInteractions(projectRepositoryMock);
//        assertEquals(2, foundProjects.size());
//    }
//
//    @Test
//    public void findAllWhenIsProjectNotFound() {
//        List<Project> projects = new ArrayList<>();
//        when(projectRepositoryMock.findAll()).thenReturn(projects);
//
//        List<Project> foundProjects = projectServiceImpl.findAll();
//
//        verify(projectRepositoryMock, times(1)).findAll();
//        verifyNoMoreInteractions(projectRepositoryMock);
//        assertEquals(0, foundProjects.size());
//    }
//
//    @Test
//    public void findById() {
//        Project project = new Project();
//        project.setId(PROJECT_ID);
//        project.setName(PROJECT_NAME);
//        when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(Optional.of(project));
//
//        Optional<Project> foundProject = projectServiceImpl.findById(PROJECT_ID);
//
//        verify(projectRepositoryMock, times(1)).findById(PROJECT_ID);
//        verifyNoMoreInteractions(projectRepositoryMock);
//        assertTrue(foundProject.isPresent());
//        assertEquals(project, foundProject.get());
//    }
//
//    @Test
//    public void findByIdWhenIsProjectNotFound() {
//        when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(Optional.empty());
//
//        Optional<Project> foundProject = projectServiceImpl.findById(PROJECT_ID);
//
//        verify(projectRepositoryMock, times(1)).findById(PROJECT_ID);
//        verifyNoMoreInteractions(projectRepositoryMock);
//        assertFalse(foundProject.isPresent());
//    }
//
//    @Test
//    public void delete() {
//        Project project = new Project();
//        when(projectRepositoryMock.findById(any())).thenReturn(Optional.of(project));
//
//        assertTrue(projectServiceImpl.delete(project));
//        verify(projectRepositoryMock, times(1)).findById(any());
//        verify(projectRepositoryMock, times(1)).delete(project);
//        verifyNoMoreInteractions(projectRepositoryMock);
//    }
//
//    @Test
//    public void deleteWhenProjectIsNull() {
//        assertFalse(projectServiceImpl.delete(null));
//        verify(projectRepositoryMock, times(0)).delete(any());
//        verifyNoMoreInteractions(projectRepositoryMock);
//    }
//
//    @Test
//    public void findByName() {
//        Project project = new Project();
//        project.setName(PROJECT_NAME);
//        when(projectRepositoryMock.findByName(project.getName())).thenReturn(Optional.of(project));
//
//        Optional<Project> foundProject = projectServiceImpl.findByName(PROJECT_NAME);
//
//        verify(projectRepositoryMock, times(1)).findByName(PROJECT_NAME);
//        verifyNoMoreInteractions(projectRepositoryMock);
//        assertTrue(foundProject.isPresent());
//        assertEquals(project, foundProject.get());
//    }
//
//    @Test
//    public void findByNameWhenIsProjectNotFound() {
//        when(projectRepositoryMock.findByName(any())).thenReturn(Optional.empty());
//
//        Optional<Project> foundProject = projectServiceImpl.findByName(any());
//
//        verify(projectRepositoryMock, times(1)).findByName(any());
//        verifyNoMoreInteractions(projectRepositoryMock);
//        assertFalse(foundProject.isPresent());
//    }
//    @Test
//    public void update() {
//        Project projectTarget = new Project();
//        projectTarget.setName("projectTarget");
//        Project projectSource = new Project();
//        projectSource.setName("projectSource");
//
//        when(projectRepositoryMock.save(projectTarget)).thenReturn(projectTarget);
//
//        Project projectTargetModified = projectServiceImpl.update(projectSource, projectTarget);
//
//        verify(projectRepositoryMock, times(1)).save(projectTarget);
//        verifyNoMoreInteractions(projectRepositoryMock);
//        assertEquals(projectTargetModified.getName(), projectSource.getName());
//    }
//
//    @Test
//    public void updateWhenParameterIsNull() {
//        Project projectTarget = new Project();
//        projectTarget.setName("projectTarget");
//        Project projectSource = new Project();
//        projectSource.setName("projectSource");
//
//        Project projectTargetModified = projectServiceImpl.update(projectSource, null);
//
//        verify(projectRepositoryMock, times(0)).save(any());
//        verifyNoMoreInteractions(projectRepositoryMock);
//        assertNull(projectTargetModified);
//    }
//}