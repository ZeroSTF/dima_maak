package tn.esprit.dima_maak.entities;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class Venture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Sector sector;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
    @Enumerated(EnumType.STRING)
    private Stage stage;
    @Column(columnDefinition = "TEXT")
    private String details;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "investment_id")
    private Investment investment;
}
