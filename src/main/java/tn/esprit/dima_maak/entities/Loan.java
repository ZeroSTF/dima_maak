package tn.esprit.dima_maak.entities;

import lombok.Data;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Data
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
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    private List<Portion> portions;
}
