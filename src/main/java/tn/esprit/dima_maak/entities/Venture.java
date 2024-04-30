package tn.esprit.dima_maak.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Location location;
    @Column(columnDefinition = "TEXT")
    private String details;
    @ManyToOne
    private User user;
    @JsonIgnore
    @OneToMany(mappedBy = "venture", cascade = CascadeType.ALL)
    private List<Investment> investments;
}
