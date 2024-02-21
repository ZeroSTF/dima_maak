package tn.esprit.dima_maak.entities;

import lombok.Data;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Data
public class Return {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float amount;
    private java.sql.Date date;
    @Enumerated(EnumType.STRING)
    private RType type;
    @ManyToOne
    @JoinColumn(name = "investment_id")
    private Investment investment;
}
