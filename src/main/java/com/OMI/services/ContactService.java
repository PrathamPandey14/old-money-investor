package com.OMI.services;

import com.OMI.entities.Contact;
import com.OMI.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private JavaMailSender mailSender;

    public Contact saveMessage(Contact contact) {
        Contact saved = contactRepository.save(contact);
        sendAcknowledgmentEmail(contact);
        return saved;
    }

    public Page<Contact> searchMessages(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isEmpty()) {
            return contactRepository.findAll(pageable);
        }
        return contactRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrSubjectContainingIgnoreCase(
                keyword, keyword, keyword, pageable
        );
    }

    @Async
    private void sendAcknowledgmentEmail(Contact contact) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(contact.getEmail());
            message.setSubject("Thank you for contacting us - RealEstate");
            message.setText("Dear " + contact.getName() + ",\n\n" +
                    "We have received your query regarding: " + contact.getSubject() + ".\n" +
                    "Our team will reach out to you shortly.\n\n" +
                    "Best Regards,\nRealEstate Team");
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteMessage(Long id) {
        contactRepository.deleteById(id);
    }

}