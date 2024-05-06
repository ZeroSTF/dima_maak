package tn.esprit.dima_maak.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Claim implements Serializable {
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
    @JsonIgnore
    @ManyToOne
    private Insurance insurance;
}
