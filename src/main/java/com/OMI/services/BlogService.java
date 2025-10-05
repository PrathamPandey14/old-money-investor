package com.OMI.services;

import com.OMI.entities.Blog;
import com.OMI.repositories.BlogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BlogService {

    private final BlogRepository repo;

    String uploadDir = System.getProperty("user.dir") + "/uploads/";

    public BlogService(BlogRepository repo) {
        this.repo = repo;
    }

    public List<Blog> saveAllBlogs(List<Blog> blogs) {
        return repo.saveAll(blogs);
    }

    public List<Blog> getAll(){
        return repo.findAll();
    }

    // Fetch paginated blogs
    public Page<Blog> findAllBlogs(Pageable pageable) {
        return repo.findAll(pageable);
    }


    public Blog getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Blog save(Blog blog, List<MultipartFile> files) {
        if (files != null) {
            List<String> imagePaths = new ArrayList<>();
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                    Path filePath = Paths.get("uploads/" + fileName);
                    try {
                        Files.write(filePath, file.getBytes());
                        imagePaths.add("/uploads/" + fileName); // save relative path
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            blog.setImageList(imagePaths);
        }
        return repo.save(blog);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<Blog> getLatest(int count) {
        return repo.findTopNByOrderByCreatedAtDesc(PageRequest.of(0, count));
    }

    // Optional: Search blogs by title/content
    public Page<Blog> searchBlogs(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isEmpty()) {
            return repo.findAll(pageable);
        }
        return repo.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword, pageable);
    }
}