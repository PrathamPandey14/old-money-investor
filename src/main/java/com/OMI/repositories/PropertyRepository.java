package com.OMI.repositories;

import com.OMI.entities.Blog;
import com.OMI.entities.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    //Latest Properties
    @Query("SELECT p FROM Property p ORDER BY p.createdAt DESC")
    List<Property> findTopNByOrderByCreatedAtDesc(Pageable pageable);

    //Featured Properties
    List<Property> findByStatusAndTransactionType(String status, String transactionType , Pageable pageable);

    //Luxury Properties (example: high price, more than 3 bedrooms)
    List<Property> findByPriceGreaterThanAndBedroomsGreaterThan(Integer price, String bedrooms , Pageable pageable);

    // 🔥 Location-based properties
    List<Property> findByLocationContainingIgnoreCase(String location);

    @Query("SELECT p FROM Property p " +
            "WHERE (:search IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(p.location) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:priceRange IS NULL OR " +
            "     (:priceRange = '0-50' AND p.price BETWEEN 0 AND 5000000) OR " +
            "     (:priceRange = '50-100' AND p.price BETWEEN 5000000 AND 10000000) OR " +
            "     (:priceRange = '100+' AND p.price >= 10000000)) " +
            "AND (:bedrooms IS NULL OR p.bedrooms = :bedrooms) " +
            "AND (:facing IS NULL OR p.facing = :facing)")
    Page<Property> searchProperties(
            @Param("search") String search,
            @Param("priceRange") String priceRange,
            @Param("bedrooms") Integer bedrooms,
            @Param("facing") String facing,
            Pageable pageable);


    // Search properties by location (case-insensitive) with pagination
    @Query("SELECT p FROM Property p " +
            "WHERE (:location IS NULL OR LOWER(p.location) LIKE LOWER(CONCAT('%', :location, '%')))")
    Page<Property> findByLocation(@Param("location") String location, Pageable pageable);

}