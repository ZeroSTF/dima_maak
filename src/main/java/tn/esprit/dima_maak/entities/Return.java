package tn.esprit.dima_maak.entities;

import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Treturn")
public class Return implements Serializable{
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
