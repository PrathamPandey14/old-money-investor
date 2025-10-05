package com.OMI.controller;

import com.OMI.entities.Contact;
import com.OMI.services.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserContactController {

    private final ContactService contactService;

    public UserContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contact")
    public String showContactForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "main/contact";
    }

    @PostMapping("/contact")
    public String submitContactForm(@ModelAttribute Contact contact, RedirectAttributes redirectAttributes) {
        contactService.saveMessage(contact);
        redirectAttributes.addFlashAttribute("success", "Your message has been sent successfully!");
        return "redirect:main/contact";
    }
}