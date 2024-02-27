package tn.esprit.dima_maak.entities;

import lombok.Data;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
@Entity
@Data
public class Return implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private java.sql.Date date;
    @Enumerated(EnumType.STRING)
    private RType returnType;
    private Float returnAmount;
    private Float returnInterest;
    private Long sharesGain;
    @ManyToOne
    @JoinColumn(name = "investment_id")
    private Investment investment;
}
