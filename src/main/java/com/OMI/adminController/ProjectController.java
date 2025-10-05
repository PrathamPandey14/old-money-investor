package com.OMI.adminController;

import com.OMI.entities.Project;
import com.OMI.services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // List all projects
    @GetMapping
    public String listProjects(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "admin/projects/list"; // Thymeleaf template: src/main/resources/templates/projects/list.html
    }

    // Show project details
    @GetMapping("/view/{id}")
    public String viewProject(@PathVariable Long id, Model model, RedirectAttributes redirectAttrs) {
        Project project = projectService.getById(id); // returns Project or null
        if (project != null) {
            model.addAttribute("project", project);
            return "admin/projects/view"; // Thymeleaf template: admin/projects/view.html
        } else {
            redirectAttrs.addFlashAttribute("error", "Project not found");
            return "redirect:/admin/projects";
        }
    }


    // Show form to create new project
    @GetMapping("/new")
    public String createProjectForm(Model model) {
        model.addAttribute("project", new Project());
        return "admin/projects/form"; // Thymeleaf template: projects/form.html
    }

    // Save new project
    @PostMapping
    public String saveProject(@ModelAttribute Project project, RedirectAttributes redirectAttrs) {
        projectService.createProject(project);
        redirectAttrs.addFlashAttribute("success", "Project created successfully");
        return "redirect:/admin/projects";
    }

    // Show form to edit existing project
    @GetMapping("/edit/{id}")
    public String editProjectForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttrs) {
        Project project = projectService.getById(id); // returns Project or null
        if (project != null) {
            model.addAttribute("project", project);
            return "admin/projects/form"; // reuse form.html for edit
        } else {
            redirectAttrs.addFlashAttribute("error", "Project not found");
            return "redirect:/admin/projects";
        }
    }


    // Update existing project
    @PostMapping("/update/{id}")
    public String updateProject(@PathVariable Long id, @ModelAttribute Project project, RedirectAttributes redirectAttrs) {
        projectService.updateProject(id, project);
        redirectAttrs.addFlashAttribute("success", "Project updated successfully");
        return "redirect:/admin/projects";
    }

    // Delete project
    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        projectService.deleteProject(id);
        redirectAttrs.addFlashAttribute("success", "Project deleted successfully");
        return "redirect:/admin/projects";
    }
}
