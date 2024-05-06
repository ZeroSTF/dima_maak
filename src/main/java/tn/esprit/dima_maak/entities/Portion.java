package tn.esprit.dima_maak.entities;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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
    private Date date;
    private double amount;
    private double Md;
    private double Inte;
    private double Amortissement;
    @Enumerated(EnumType.STRING)
    private PStatus status;
    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;
    @OneToOne(mappedBy = "portion", cascade = CascadeType.ALL)
    private Penalty penalty;
}
