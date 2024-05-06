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
public class  Insurance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private float clientcoverageamount;
    private float clientpremium;
    @Enumerated(EnumType.STRING)
    private  InStatus state;

    @JsonIgnore
    @ManyToOne
    private User user;
    @JsonIgnore
    @OneToMany(mappedBy = "insurance",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Claim> claims;
    @JsonIgnore
    @OneToMany(mappedBy = "insurance",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Premium> premiums;

    @ManyToOne
    private InsuranceP insuranceP;
}
