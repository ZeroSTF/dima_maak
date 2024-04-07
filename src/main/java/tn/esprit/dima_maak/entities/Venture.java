package tn.esprit.dima_maak.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter

public class Venture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idV;
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
    private LocalDate dateExp;
    @Enumerated(EnumType.STRING)
    private IStatus status;
    private Float loanAmount;
    private Float interest;
    private Long loanDuration;
    private  Float dividendPerShare;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @Column(columnDefinition = "TEXT")
    private String details;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

   @JsonIgnore
   @OneToMany(mappedBy = "venture", cascade = CascadeType.ALL)
    private List<Investment> investments;
}
