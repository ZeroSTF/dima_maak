package tn.esprit.dima_maak.entities;

import lombok.*;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rating implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int rating;
    @ManyToOne
    private User user;
    @ManyToOne
    private Comment comment;
}
