package tn.esprit.dima_maak.entities;

import lombok.Data;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Data
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private java.sql.Date startDate;
    private java.sql.Date endDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "insurance", cascade = CascadeType.ALL)
    private List<Claim> claims;
    @OneToMany(mappedBy = "insurance", cascade = CascadeType.ALL)
    private List<Premium> premiums;
    @ManyToOne
    @JoinColumn(name = "insuranceP_id")
    private InsuranceP insuranceP;
}
