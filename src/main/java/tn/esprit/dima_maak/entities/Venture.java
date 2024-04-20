package tn.esprit.dima_maak.entities;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Venture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Sector sector;
    @OneToOne(cascade = CascadeType.ALL)
    private Location location;
    @Enumerated(EnumType.STRING)
    private Stage stage;
    @Column(columnDefinition = "TEXT")
    private String details;
    @ManyToOne
    private User user;
    @ManyToOne
    private Investment investment;
}
