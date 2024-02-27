package tn.esprit.dima_maak.entities;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Treturn")
public class Return {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float amount;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private RType type;
    @ManyToOne
    private Investment investment;
}
