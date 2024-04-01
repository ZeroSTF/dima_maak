package tn.esprit.dima_maak.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;
/*import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;*/

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cin;
    private String name;
    private String surname;
    @OneToOne(cascade = CascadeType.ALL)
    private Location address;
    private LocalDate birthDate;
    private String email;
    private String password;
    private Float salary;
    private String job;
    private String photo;
    private Float balance;
    private Long rib;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private UStatus status;
    private Integer lp;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Loyalty> loyalties;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notifications;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Loan> loans;
    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Insurance> insurances;
}

