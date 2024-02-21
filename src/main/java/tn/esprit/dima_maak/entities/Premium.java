package tn.esprit.dima_maak.entities;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class Premium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private java.sql.Date date;
    private Float amount;
    private boolean status;
    private Float accumulatedInterest;
    @ManyToOne
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;
}
