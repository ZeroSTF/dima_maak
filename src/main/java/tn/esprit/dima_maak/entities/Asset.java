package tn.esprit.dima_maak.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Asset {

   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Id
   @Column(unique = true)
    private Long assetid ;
    @Enumerated(EnumType.STRING)
    private  AssetType type ;
    private  String description ,image;
    private  Float  initialAmount,price ;
    private LocalDate purchasedate ;
    private  LocalDate WarrantyExpirationDate ;
    private    String         MaintenanceStatus ;
      private  LocalDate LastMaintenanceDate ;
      private  String Functions ;
      private  Float Power ;
      private  Float ProductionCapacity ;
      private  String OperationalEfficiency ;
      private  String ServiceLevel  ;
      private  String FuelConsumption ;
      private  String EngineCondition ;
      private  Float Mileage ;
      private Float annualInterestRate;
      @JsonIgnore
      @OneToMany(mappedBy ="asset",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
      private List<Demande> demandeList;
@JsonIgnore
    @ManyToOne
    User user;


}

