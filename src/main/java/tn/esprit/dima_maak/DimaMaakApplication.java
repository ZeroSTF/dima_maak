package tn.esprit.dima_maak;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import tn.esprit.dima_maak.entities.Role;
import tn.esprit.dima_maak.entities.TypeRole;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.repositories.RoleRepository;
import tn.esprit.dima_maak.repositories.UserRepository;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Arrays;
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
            if(roleRepository.findByType(TypeRole.ADMIN).isPresent() && roleRepository.findByType(TypeRole.USER).isPresent() && roleRepository.findByType(TypeRole.INVESTOR).isPresent()) return;
            roleRepository.save(new Role(null, "ADMIN", TypeRole.ADMIN));
            roleRepository.save(new Role(null, "USER", TypeRole.USER));
            roleRepository.save(new Role(null, "USER", TypeRole.INVESTOR));
        };


    }
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Authorization", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
