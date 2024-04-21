package tn.esprit.dima_maak.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDate;



@Entity
@Getter
@Setter
public class Leasing {

   @GeneratedValue(strategy = GenerationType.AUTO)
   @Id
   @Column(unique = true)
    private  Long leaseid ;

    private LocalDate startdate;
    private LocalDate enddate;
    private float monthlypayment;
    @Enumerated
    private LStatus status ;
    private  Float depositamount ;
    private  Float penaltyfee ;
    private  Float additionalfee ;
    private  Boolean renwealoption ;
    @Enumerated
    private  PStatus paymentstatus ;

    @OneToOne(mappedBy = "leasing")
    private Asset asset;
    @OneToOne
    private User user;
}

