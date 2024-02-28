package tn.esprit.dima_maak.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter

public class Venture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String ventureName;
    @Enumerated(EnumType.STRING)
    private VType ventureType;
    private String description;
    @Enumerated(EnumType.STRING)
    private Stage stage;
    @Enumerated(EnumType.STRING)
    private Sector sector;
    private  Long availableShares;
    private Float sharesPrice;
    @Enumerated(EnumType.STRING)
    private IStatus status;
    private Float loanAmount;
    private Float interest;
    private LocalDate loanDuration;
    private  Float dividendPerShare;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @Column(columnDefinition = "TEXT")
    private String details;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "investment_id")
    private Investment investment;
}
