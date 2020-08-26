package com.petHosting.config;

import com.petHosting.entity.Role;
import com.petHosting.entity.User;
import com.petHosting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;



    @Override
    public void run(String... args) throws Exception {
        User admin = userRepository.findByEmail("admin@yahoo.com");
        if(admin == null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            User user = new User();
            user.setEmail("admin@yahoo.com");
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setEnabled(true);
            user.setCreatedAt(new Date(System.currentTimeMillis()));
            user.setCreatedBy("system");
            user.setUpdatedAt(new Date(System.currentTimeMillis()));
            user.setUpdatedBy("system");
            Set<Role> roles = Set.of(new Role("Admin"));
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}
