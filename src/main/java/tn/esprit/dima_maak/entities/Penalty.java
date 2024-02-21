package tn.esprit.dima_maak.entities;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Penalty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private java.sql.Date date;
    private Float amount;
    private boolean status;
    @OneToOne
    @JoinColumn(name = "portion_id")
    private Portion portion;
}
