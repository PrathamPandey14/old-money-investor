package com.OMI.adminController;

import com.OMI.entities.Property;
import com.OMI.services.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin/properties")
public class PropertyController {

    private final PropertyService service;

    public PropertyController(PropertyService service) {
        this.service = service;
    }

    // LIST ALL PROPERTIES
    @GetMapping
    public String list(Model model) {
        model.addAttribute("properties", service.getAll());
        return "admin/properties/list";
    }

    // CREATE FORM
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("propertyDto", new Property());
        model.addAttribute("amenitiesList", Property.amenities.values()); // Enum list for dropdown
        return "admin/properties/create";
    }

    // CREATE PROPERTY
    @PostMapping("/create")
    public String create(@ModelAttribute("propertyDto") Property property,
                         @RequestParam(value = "files", required = false) List<MultipartFile> files) {
        service.save(property, files);
        return "redirect:/admin/properties";
    }

    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Property property = service.getById(id);
        if (property == null) {
            return "redirect:/admin/properties";
        }
        model.addAttribute("propertyDto", property);
        model.addAttribute("amenitiesList", Property.amenities.values()); // Enum list for dropdown
        return "admin/properties/edit";
    }

    // UPDATE PROPERTY
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @ModelAttribute("propertyDto") Property property,
                       @RequestParam(value = "files", required = false) List<MultipartFile> files) {
        property.setId(id);
        service.save(property, files);
        return "redirect:/admin/properties";
    }

    // DELETE PROPERTY
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/admin/properties";
    }
}