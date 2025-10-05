package com.OMI.adminController;

import com.OMI.entities.Blog;
import com.OMI.services.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin/blogs")
public class BlogController {
    private final BlogService service;

    public BlogController(BlogService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("blogs", service.getAll());
        return "admin/blogs/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("blogDto", new Blog());
        return "admin/blogs/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Blog blog,
                         @RequestParam(value = "files", required = false) List<MultipartFile> files) {
        service.save(blog, files);
        return "redirect:/admin/blogs";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Blog blog = service.getById(id);
        if (blog == null) {
            return "redirect:/admin/blogs";
        }
        model.addAttribute("blogDto", blog);
        return "admin/blogs/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @ModelAttribute Blog blog,
                       @RequestParam(value = "files", required = false) List<MultipartFile> files) {
        blog.setId(id);
        service.save(blog, files);
        return "redirect:/admin/blogs";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/admin/blogs";
    }
}