package tn.esprit.dima_maak.entities;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime createdDate;
    @ManyToOne
    private User user;
    @ManyToOne
    private Post post;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Rating> ratings;
}
