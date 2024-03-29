package tn.esprit.dima_maak.entities;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private IType type;
    private Integer duration;
    private Float coverageAmount;
    private Float premium;
    @Column(columnDefinition = "TEXT")
    private String policy;
}

