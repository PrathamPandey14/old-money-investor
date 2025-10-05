package com.OMI.services;

import com.OMI.entities.Project;
import com.OMI.entities.Property;
import com.OMI.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

//    public Optional<Project> getProjectById(Long id) {
//        return projectRepository.findById(id);
//    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project projectDetails) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setName(projectDetails.getName());
        project.setLocation(projectDetails.getLocation());
        project.setDeveloperName(projectDetails.getDeveloperName());
        project.setDeveloperWebsite(projectDetails.getDeveloperWebsite());
        project.setDeveloperContact(projectDetails.getDeveloperContact());
        project.setStatus(projectDetails.getStatus());
        project.setTotalUnits(projectDetails.getTotalUnits());
        project.setPriceRange(projectDetails.getPriceRange());
        project.setLaunchDate(projectDetails.getLaunchDate());
        project.setCompletionDate(projectDetails.getCompletionDate());
        project.setDescription(projectDetails.getDescription());
        project.setAmenities(projectDetails.getAmenities());
        project.setImages(projectDetails.getImages());
        project.setUpdatedAt(new java.util.Date());

        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}