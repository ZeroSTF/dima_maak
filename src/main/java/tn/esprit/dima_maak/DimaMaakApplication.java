package tn.esprit.dima_maak;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.esprit.dima_maak.entities.Role;
import tn.esprit.dima_maak.entities.TypeRole;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.repositories.RoleRepository;
import tn.esprit.dima_maak.repositories.UserRepository;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class DimaMaakApplication {

    public static void main(String[] args) {
        SpringApplication.run(DimaMaakApplication.class, args);
    }

    /////////////////////////////////////// Roles to be added by default on startup ///////////////////////////////////////////////////////
    @Bean
    CommandLineRunner run(RoleRepository roleRepository){
        return args -> {
            if(roleRepository.findByAuthority("ADMIN").isPresent()&&roleRepository.findByAuthority("USER").isPresent()) return;
            Role adminRole = roleRepository.save(new Role(null, "ADMIN", TypeRole.ADMIN));
            roleRepository.save(new Role(null, "USER", TypeRole.USER));
            roleRepository.save(new Role(null, "USER", TypeRole.Investor));
        };
    }

}
