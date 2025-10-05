package com.OMI.controller;

import com.OMI.entities.Blog;
import com.OMI.services.BlogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/blogs")
public class UserBlogController {
    private final BlogService blogService;

    public UserBlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    // PUBLIC – list page accessible to everyone
    // ✅ List all blogs with pagination
    @GetMapping
    public String listBlogs(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "6") int size,
                            @RequestParam(required = false) String keyword) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Blog> blogPage;

        if (keyword != null && !keyword.isEmpty()) {
            blogPage = blogService.searchBlogs(keyword, pageable);
            model.addAttribute("keyword", keyword);
        } else {
            blogPage = blogService.findAllBlogs(pageable);
        }

        model.addAttribute("blogs", blogPage.getContent());
        model.addAttribute("page", blogPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", blogPage.getTotalPages());


        return "blogs/list"; // Thymeleaf template (blogs/list.html)
    }

    @GetMapping("/details/{id}")
    public String getBlogDetails(@PathVariable Long id, Model model) {
        Blog blog = blogService.getById(id);

        if (blog == null) {
            return "error/404"; // or redirect to an error page
        }

        model.addAttribute("blog", blog);
        return "blogs/details";
    }

}
