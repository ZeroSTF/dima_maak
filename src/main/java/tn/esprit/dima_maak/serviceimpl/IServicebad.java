package tn.esprit.dima_maak.serviceimpl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;


@Repository
public interface IServicebad {
    public ResponseEntity<String> filterBadWords1(String text);

}
