package tn.esprit.dima_maak.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceP implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
   // @Column(nullable = false)
    private IType type;
    private Integer duration;
    private Float coverageAmount;
    private Float premium;
    @Column(columnDefinition = "TEXT")
    private String policy;
    @JsonIgnore
    @OneToMany(mappedBy ="insuranceP" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Insurance> insurances;
}

