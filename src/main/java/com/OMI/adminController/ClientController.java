package com.OMI.adminController;

import com.OMI.entities.Client;
import com.OMI.services.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // DTO for row + color
    public static class ClientRow {
        private final Client client;
        private final String rowColor;

        public ClientRow(Client client, String rowColor) {
            this.client = client;
            this.rowColor = rowColor;
        }

        public Client getClient() { return client; }
        public String getRowColor() { return rowColor; }
    }

    @GetMapping
    public String listClients(Model model) {
        List<Client> clients = clientService.getAllClients();
        LocalDate today = LocalDate.now();
        String todayStr = today.toString();

        List<ClientRow> clientRows = clients.stream().map(client -> {
            String rowColor = "";
            if (client.getFollowUpDate() != null) {
                if (client.getFollowUpDate().isEqual(today)) {
                    rowColor = "bg-yellow-100"; // follow-up today
                } else if (client.getFollowUpDate().isBefore(today)) {
                    rowColor = "bg-red-100";    // overdue
                }
            }
            return new ClientRow(client, rowColor);
        }).collect(Collectors.toList());

        long followUpsToday = clients.stream()
                .filter(c -> c.getFollowUpDate() != null && c.getFollowUpDate().isEqual(today))
                .count();

        long overdueFollowUps = clients.stream()
                .filter(c -> c.getFollowUpDate() != null && c.getFollowUpDate().isBefore(today))
                .count();

        model.addAttribute("clientRows", clientRows);
        model.addAttribute("todayStr", todayStr);
        model.addAttribute("followUpsToday", followUpsToday);
        model.addAttribute("overdueFollowUps", overdueFollowUps);

        return "admin/clients/create"; // table view
    }

    @PostMapping("/save")
    public String saveClient(@ModelAttribute Client client, Model model) {
        LocalDate today = LocalDate.now();

        if (client.getFollowUpDate() != null && client.getFollowUpDate().isBefore(today)) {
            model.addAttribute("error", "Follow-up date cannot be in the past!");

            // Reload client list
            List<Client> clients = clientService.getAllClients();
            List<ClientRow> clientRows = clients.stream().map(c -> {
                String rowColor = "";
                if (c.getFollowUpDate() != null) {
                    if (c.getFollowUpDate().isEqual(today)) rowColor = "bg-yellow-100";
                    else if (c.getFollowUpDate().isBefore(today)) rowColor = "bg-red-100";
                }
                return new ClientRow(c, rowColor);
            }).collect(Collectors.toList());

            long followUpsToday = clients.stream()
                    .filter(c -> c.getFollowUpDate() != null && c.getFollowUpDate().isEqual(today))
                    .count();

            long overdueFollowUps = clients.stream()
                    .filter(c -> c.getFollowUpDate() != null && c.getFollowUpDate().isBefore(today))
                    .count();

            model.addAttribute("clientRows", clientRows);
            model.addAttribute("todayStr", today.toString());
            model.addAttribute("followUpsToday", followUpsToday);
            model.addAttribute("overdueFollowUps", overdueFollowUps);

            return "admin/clients/create"; // stay on table page
        }

        clientService.saveClient(client);
        return "redirect:/admin/clients";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return "redirect:/admin/clients";
    }
}