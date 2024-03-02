package tn.esprit.dima_maak.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Asset {

   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Id
    private Long asstid ;
    @Enumerated
    private  AssetType type ;
    private  String description ;
    private  Float value ;
    private LocalDate purchasedate ;
    private  LocalDate warrantyexprirationDate ;
    @OneToOne

    private Leasing leasing;

}

