package tn.esprit.dima_maak.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class ChargeRequest implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String email;
    private String cardNumber;
    private String exp_month;
    private String exp_year;
    private String cvv;
    private Long amount;
    private String currency;



}
