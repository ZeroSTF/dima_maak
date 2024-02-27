package tn.esprit.dima_maak.entities;

import lombok.Data;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class Investment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private java.sql.Date date;
    private Long purchasedShares;
    private Float amount;
    @Enumerated(EnumType.STRING)
    private INStatus status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "investment", cascade = CascadeType.ALL)
    private List<Venture> ventures;
}
