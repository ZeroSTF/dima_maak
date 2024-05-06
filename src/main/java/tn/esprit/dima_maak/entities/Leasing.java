package tn.esprit.dima_maak.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.io.Serializable;
import java.time.LocalDate;



@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Leasing  {

   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Id
   @Column(unique = true)
    private  Long leaseid ;

    private LocalDate startdate;
    private LocalDate enddate;
    private float monthlypayment;
    @Enumerated
    private LStatus status ;
    private  Float penaltyfee ;
    private  Boolean renwealoption ;
    @Enumerated(EnumType.STRING)
    private  PStatus paymentstatus ;
    //admin
    private float initialValue;
//@JsonIgnore
//    @OneToOne(mappedBy = "leasing")
//    private Asset asset;
 @JsonIgnore
    @ManyToOne
    private User user;
 @JsonIgnore
    @ManyToOne
    Demande demande;
}

