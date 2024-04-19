package tn.esprit.dima_maak.entities;

import lombok.*;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;


import java.util.List;

@Getter
@Setter
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private java.sql.Date date;
    private Float amount;
    private Float unpaidAmount;
    private Integer termInMonths;
    private Float interest;
    private java.sql.Date startDate;
    @Enumerated(EnumType.STRING)
    private LType purpose;
    @Enumerated(EnumType.STRING)
    private LStatus status;
    @Enumerated
    private LMethod PaimentMethod;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    private List<Portion> portions;
}
