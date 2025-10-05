package com.OMI.adminController;

import com.OMI.entities.Contact;
import com.OMI.repositories.ContactRepository;
import com.OMI.services.ContactService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/messages")
public class ContactController {


    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    // Admin messages with pagination & sorting
    @GetMapping()
    public String viewMessages(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sortField,
            @RequestParam(defaultValue = "desc") String sortDir,
            Model model) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Contact> messagePage = contactService.searchMessages(search, pageable);

        model.addAttribute("messages", messagePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", messagePage.getTotalPages());
        model.addAttribute("totalItems", messagePage.getTotalElements());
        model.addAttribute("search", search);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "admin/messages";
    }

    @PostMapping("/delete/{id}")
    public String deleteMessage(@PathVariable Long id,
                                @RequestParam(required = false) String search,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                @RequestParam(defaultValue = "createdAt") String sortField,
                                @RequestParam(defaultValue = "desc") String sortDir) {
        contactService.deleteMessage(id);

        return "redirect:/admin/messages?page=" + page + "&size=" + size +
                "&sortField=" + sortField + "&sortDir=" + sortDir +
                (search != null ? "&search=" + search : "");
    }

}