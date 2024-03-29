package tn.esprit.dima_maak;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.esprit.dima_maak.entities.Role;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.repositories.RoleRepository;
import tn.esprit.dima_maak.repositories.UserRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class DimaMaakApplication {

    public static void main(String[] args) {
        SpringApplication.run(DimaMaakApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder){
        return args -> {
            if(roleRepository.findByAuthority("ADMIN").isPresent()) return;
            Role adminRole = roleRepository.save(new Role(null, "ADMIN"));
            roleRepository.save(new Role(null, "USER"));
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            User riadhAdmin = new User( null,11440440L, "Chnitir", "Riadh", null, LocalDate.of(2001, 6, 5), "riadh.chnitir@esprit.tn", passwordEncoder.encode("111111"), 5000f, "Engineer", null, 500000f, null, roles, null, null, null, null, null);
            userRepository.save(riadhAdmin);
        };
    }

}
