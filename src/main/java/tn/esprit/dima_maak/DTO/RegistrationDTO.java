package tn.esprit.dima_maak.DTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import lombok.*;
import tn.esprit.dima_maak.entities.Location;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RegistrationDTO {
    private Long cin;
    private String name;
    private String surname;
    private Location address;
    private LocalDate birthDate;
    private String email;
    private String password;
    private Float salary;
    private String job;
    private Long rib;
}
