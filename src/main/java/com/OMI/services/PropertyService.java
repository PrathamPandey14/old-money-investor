package com.OMI.services;

import com.OMI.entities.Property;
import com.OMI.repositories.PropertyRepository;
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
public class PropertyService {

    private final PropertyRepository repo;

    String uploadDir = System.getProperty("user.dir") + "/uploads/";


    public PropertyService(PropertyRepository repo) {
        this.repo = repo;
    }

    public List<Property> saveAllProperties(List<Property> properties) {
        return repo.saveAll(properties);
    }

    // Get all properties
    public List<Property> getAll() {
        return repo.findAll();
    }

    // Get property by ID
    public Property getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    // Save / Update property
    public Property save(Property property, List<MultipartFile> files) {
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
            property.setImageList(imagePaths);
        }
        return repo.save(property);
    }

    // Delete property
    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<Property> getLatest(int count) {
        return repo.findTopNByOrderByCreatedAtDesc(PageRequest.of(0, count));
    }

    // Featured properties (e.g., Available properties for Sale)
    public List<Property> getFeaturedProperties(int count) {
        return repo.findByStatusAndTransactionType("Available", "Sale" , PageRequest.of(0,count));
    }

    // Luxury properties (example criteria: price > 1,00,00,000 and bedrooms > 3)
    public List<Property> getLuxuryProperties(int count) {
        return repo.findByPriceGreaterThanAndBedroomsGreaterThan(10000000, "3" , PageRequest.of(0,count) );
    }

    public List<Property> getPropertiesByLocation(String location) {
        return repo.findByLocationContainingIgnoreCase(location);
    }

    public Page<Property> searchProperties(String search, String priceRange, Integer bedrooms, String facing, Pageable pageable) {
        return repo.searchProperties(
                search != null && !search.isEmpty() ? search : null,
                priceRange != null && !priceRange.isEmpty() ? priceRange : null,
                bedrooms,
                facing != null && !facing.isEmpty() ? facing : null,
                pageable
        );
    }


    public Page<Property> searchByLocation(String location, Pageable pageable) {
        return repo.findByLocation(location, pageable);
    }

}