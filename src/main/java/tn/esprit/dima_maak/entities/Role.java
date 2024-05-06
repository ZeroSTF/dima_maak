package tn.esprit.dima_maak.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String authority;
    @Enumerated(EnumType.STRING)
    private TypeRole type;
    public Role(String authority){
        this.authority = authority;
    }
    @Override
    public String getAuthority() {
        return this.authority;
    }

//ROLE_USER,
//ROLE_MODERATOR,
//ROLE_ADMIN
}