package tn.esprit.dima_maak.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Tportion")
public class Portion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private Float amount;
    @Enumerated(EnumType.STRING)
    private PStatus status;
    @ManyToOne
    private Loan loan;
    @OneToOne(mappedBy = "portion", cascade = CascadeType.ALL)
    private Penalty penalty;
}
