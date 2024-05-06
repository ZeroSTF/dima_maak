package tn.esprit.dima_maak.entities;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Investment implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private Long purchasedShares;
    private Float amount;
    private Float totalInvestment;
    @Enumerated(EnumType.STRING)
    private INStatus status;
    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;


    //@JsonIgnore
    @ManyToOne
    Venture venture;

    @JsonIgnore
    @OneToMany(mappedBy = "investment", cascade = CascadeType.ALL)
    private List<Return> returns;




}
