package tn.esprit.dima_maak.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cin;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String email;
    private String password;
    private Float salary;
    private String job;
    private String photo;
    private Float balance;
    private  Float CreditScore ;
    private Long rib;
    @ManyToMany(fetch=FetchType.EAGER)
    private Set<Role> role;
    @Enumerated(EnumType.STRING)
    private UStatus status;
    private Integer lp;
    @OneToOne(cascade = CascadeType.ALL)
    private Location address;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Loyalty> loyalties;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notifications;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Loan> loans;
    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Leasing> leasing;
    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Asset> assets;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Demande> demandeList;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Investment> investments;
    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Leasing leasing;
    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Insurance> insurances;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public User(Long cin, String name, String surname, Location address, LocalDate birthDate, String email, String password, Float salary, String job, Long rib) {
        this.cin = cin;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.salary = salary;
        this.job = job;
        this.rib = rib;
        this.setPhoto("default.jpg");

        // Set default values for other fields
        this.role = new HashSet<>(); // Initialize empty set for roles
        this.status = UStatus.Pending; // Set default status to PENDING
        this.lp = 0; // Set loyalty points to 0
        this.balance = 0f; // Set balance to 0
        this.CreditScore = 0f; // Set credit score to 0
        this.loyalties = new ArrayList<>(); // Initialize empty list for loyalties
        this.notifications = new ArrayList<>(); // Initialize empty list for notifications
        this.loans = new ArrayList<>(); // Initialize empty list for loans
        this.insurances = new HashSet<>(); // Initialize empty set for insurances
    }
}



