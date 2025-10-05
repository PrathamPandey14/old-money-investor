package com.OMI.controller;


import com.OMI.entities.Property;
import com.OMI.services.PropertyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/properties")
public class UserPropertyController {

    private final PropertyService propertyService;

    public UserPropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping()
    public String listProperties(
            Model model,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String priceRange,
            @RequestParam(required = false) Integer bedrooms,
            @RequestParam(required = false) String facing,
            @RequestParam(defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, 6); // 9 properties per page
        Page<Property> propertiesPage = propertyService.searchProperties(search, priceRange, bedrooms, facing, pageable);

        model.addAttribute("propertiesPage", propertiesPage);
        model.addAttribute("properties", propertiesPage.getContent());
        model.addAttribute("param", new SearchParam(search, priceRange, bedrooms, facing)); // helper object for Thymeleaf

        return "properties/list";
    }

    @GetMapping("/details/{id}")
    public String getPropertyDetails(@PathVariable Long id, Model model) {
        Property property = propertyService.getById(id);

        if (property == null) {
            return "error/404"; // or redirect to an error page
        }

        model.addAttribute("property", property);
        return "properties/details"; // maps to property-details.html
    }

    // Helper DTO for Thymeleaf to retain filter values
    public static class SearchParam {
        private String search;
        private String priceRange;
        private Integer bedrooms;
        private String facing;

        public SearchParam(String search, String priceRange, Integer bedrooms, String facing) {
            this.search = search;
            this.priceRange = priceRange;
            this.bedrooms = bedrooms;
            this.facing = facing;
        }

        // getters
        public String getSearch() { return search; }
        public String getPriceRange() { return priceRange; }
        public Integer getBedrooms() { return bedrooms; }
        public String getFacing() { return facing; }
    }
}
