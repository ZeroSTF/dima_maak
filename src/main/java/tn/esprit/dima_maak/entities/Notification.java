package tn.esprit.dima_maak.entities;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private NType type;
    private String content;
    private LocalDateTime date;
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
