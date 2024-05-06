package tn.esprit.dima_maak.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.dima_maak.entities.Role;
import tn.esprit.dima_maak.entities.User;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginResponseDTO {
    private String fullName;
    private String jwt;



}
