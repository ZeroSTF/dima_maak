package tn.esprit.dima_maak.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity

public class Asset {

   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Id
    private Long id ;
    @Enumerated
    private  AssetType type ;
    private  String description ;
    private  Float value ;
    private LocalDate purchasedate ;
    private  LocalDate warrantyexprirationDate ;
    @OneToOne

    private Leasing leasing;

}

