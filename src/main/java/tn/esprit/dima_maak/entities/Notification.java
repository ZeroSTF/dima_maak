package tn.esprit.dima_maak.entities;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private User user;

    public Notification(User user, NType type, String content){
        this.user=user;
        this.type=type;
        this.content=content;

        this.date=LocalDateTime.now();
        this.status=false;
    }
}
