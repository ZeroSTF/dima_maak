package tn.esprit.dima_maak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class DimaMaakApplication {

    public static void main(String[] args) {
        SpringApplication.run(DimaMaakApplication.class, args);
    }

}
