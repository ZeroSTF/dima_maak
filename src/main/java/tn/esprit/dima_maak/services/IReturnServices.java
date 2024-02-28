package tn.esprit.dima_maak.services;

import tn.esprit.dima_maak.entities.Investment;
import tn.esprit.dima_maak.entities.Return;

import java.util.List;

public interface IReturnServices {

    Return addReturn(Return treturn);
    Return updateReturn(Return treturn);
    boolean deleteReturn (Long idR);
    List<Return> getAllReturn();
    Return getReturnById(Long idR);

}
