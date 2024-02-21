package tn.esprit.dima_maak.entities;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private java.sql.Timestamp date;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String description;
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
