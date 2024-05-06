package tn.esprit.dima_maak.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime createdDate;
    private  int Rating ;
    @ManyToOne
    private User user;
    @JsonIgnore
    @ManyToOne
    private Post post;
 //   @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
   // private List<Rating> ratings;

}
