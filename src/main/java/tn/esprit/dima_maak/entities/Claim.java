package tn.esprit.dima_maak.entities;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String image;
    @Column(columnDefinition = "TEXT")
    private String details;
    private Float amount;
    @Enumerated(EnumType.STRING)
    private CStatus status;
    @ManyToOne
    private Insurance insurance;
}
