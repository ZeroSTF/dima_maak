package tn.esprit.dima_maak.entities;

import lombok.Data;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Data
public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float amount;
    private java.sql.Date date;
    @Enumerated(EnumType.STRING)
    private IStatus status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "investment", cascade = CascadeType.ALL)
    private List<Venture> ventures;
}
