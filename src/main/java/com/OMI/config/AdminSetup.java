package com.OMI.config;

import com.OMI.entities.Admin;
import com.OMI.repositories.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminSetup {

    @Bean
    CommandLineRunner initAdmin(AdminRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.count() == 0) {
                Admin admin = new Admin();
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(encoder.encode("admin123"));
                repo.save(admin);
            }
        };
    }

}
