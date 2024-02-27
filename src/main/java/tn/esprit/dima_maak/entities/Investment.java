package tn.esprit.dima_maak.entities;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float amount;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private IStatus status;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "investment", cascade = CascadeType.ALL)
    private List<Venture> ventures;
    @OneToMany(mappedBy = "investment", cascade = CascadeType.ALL)
    private Set<Return> returns;
}
