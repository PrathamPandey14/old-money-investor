package com.OMI.controller;

import com.OMI.entities.Blog;
import com.OMI.entities.Project;
import com.OMI.services.BlogService;
import com.OMI.services.ProjectService;
import com.OMI.services.PropertyService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class AuthController {

    private final BlogService blogService;

    private final PropertyService propertyService;

    private final ProjectService projectService;

    public AuthController(BlogService blogService, PropertyService propertyService, ProjectService projectService) {
        this.blogService = blogService;
        this.propertyService = propertyService;
        this.projectService = projectService;
    }

    // PUBLIC – list page accessible to everyone
    @GetMapping
    public String blogList(Model model) {
        model.addAttribute("blogs", blogService.findAllBlogs(Pageable.ofSize(6)));

        // Add latest 3–5 blogs for index page
        model.addAttribute("latestBlogs", blogService.getLatest(3)); // <-- you’ll add this in service

        model.addAttribute("property", propertyService.getAll());

        // Add latest 3–5 properties for index page
        model.addAttribute("latestProperties", propertyService.getLatest(8)); // <-- you’ll add this in service

        model.addAttribute("featuredProperties",propertyService.getFeaturedProperties(8));

        model.addAttribute("luxuryProperties",propertyService.getLuxuryProperties(8));

        model.addAttribute("projects", projectService.getAllProjects());
        return "index"; // public list page
    }

    @GetMapping("/projects/details/{id}")
    public String getProjectDetails(@PathVariable Long id, Model model) {
        Project project = projectService.getById(id);

        if (project == null) {
            return "error/404"; // or redirect to an error page
        }

        model.addAttribute("project", project);
        return "projects/details";
    }

    @GetMapping("/privacy")
    public String privacy(){
        return "/main/privacy";
    }

    @GetMapping("/terms")
    public String terms(){
        return "/main/terms";
    }

    @GetMapping("/services")
    public String services(){
        return "/main/services";
    }

}