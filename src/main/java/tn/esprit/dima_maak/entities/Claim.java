package tn.esprit.dima_maak.entities;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private java.sql.Date date;
    private String image;
    @Column(columnDefinition = "TEXT")
    private String details;
    private Float amount;
    @Enumerated(EnumType.STRING)
    private CStatus status;
    @ManyToOne
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;
}
