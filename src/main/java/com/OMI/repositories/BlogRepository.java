package com.OMI.repositories;

import com.OMI.entities.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query("SELECT b FROM Blog b ORDER BY b.createdAt DESC")
    List<Blog> findTopNByOrderByCreatedAtDesc(Pageable pageable);

    Page<Blog> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content, Pageable pageable);


}