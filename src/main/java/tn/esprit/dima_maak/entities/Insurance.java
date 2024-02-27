package tn.esprit.dima_maak.entities;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "insurance", cascade = CascadeType.ALL)
    private List<Claim> claims;
    @OneToMany(mappedBy = "insurance", cascade = CascadeType.ALL)
    private List<Premium> premiums;
    @ManyToOne
    private InsuranceP insuranceP;
}
