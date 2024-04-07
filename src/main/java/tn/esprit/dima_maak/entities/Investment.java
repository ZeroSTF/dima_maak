package tn.esprit.dima_maak.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Investment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private Long purchasedShares;
    private Float amount;
    private Float totalInvestment;
    @Enumerated(EnumType.STRING)
    private INStatus status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @JsonIgnore
    @ManyToOne
    /*@JoinColumn(name = "venture_id")*/
    Venture venture;
    @JsonIgnore
    @OneToMany(mappedBy = "investment", cascade = CascadeType.ALL)
    private List<Return> returns;

   /* public static class ReturnStats {
        private int numberOfReturns;
        private float totalReturnInterest;

        public ReturnStats(int numberOfReturns, double totalReturnInterest) {
            this.numberOfReturns = numberOfReturns;
            this.totalReturnInterest = (float) totalReturnInterest;
        }
    }*/



}
