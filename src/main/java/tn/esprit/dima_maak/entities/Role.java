package tn.esprit.dima_maak.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String authority;
    @Override
    public String getAuthority() {
        return this.authority;
    }
//ROLE_USER,
//ROLE_MODERATOR,
//ROLE_ADMIN
}