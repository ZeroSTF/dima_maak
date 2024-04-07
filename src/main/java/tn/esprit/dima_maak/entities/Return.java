package tn.esprit.dima_maak.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Treturn")
public class Return implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idR;
    private LocalDate rDate;
    @Enumerated(EnumType.STRING)
    private RType returnType;
    private Float returnAmount;
    private Float returnInterest;
    private Long sharesGain;
    @ManyToOne
    private Investment investment;


}
