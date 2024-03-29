package tn.esprit.dima_maak.entities;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
