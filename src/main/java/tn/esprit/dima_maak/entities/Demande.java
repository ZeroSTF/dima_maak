package tn.esprit.dima_maak.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private LocalDate requestDate;
    @Enumerated(EnumType.STRING)
    private LStatus status ;
@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")

    private User user;
    @JsonIgnore
   @OneToMany(mappedBy = "demande")
    List<Leasing> leasingList;

}
