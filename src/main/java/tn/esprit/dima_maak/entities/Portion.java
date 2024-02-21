package tn.esprit.dima_maak.entities;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Portion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private java.sql.Date date;
    private Float amount;
    @Enumerated(EnumType.STRING)
    private PStatus status;
    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;
    @OneToOne(mappedBy = "portion", cascade = CascadeType.ALL)
    private Penalty penalty;
}
