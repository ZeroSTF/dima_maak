package tn.esprit.dima_maak.entities;

import lombok.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Complaint implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String description;
    private boolean status;
    @ManyToOne
    private User user;
}
