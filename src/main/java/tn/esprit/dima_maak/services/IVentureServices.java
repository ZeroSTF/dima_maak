package tn.esprit.dima_maak.services;

import tn.esprit.dima_maak.entities.Venture;

import java.util.List;

public interface IVentureServices {

    Venture addVenture(Venture venture);
    Venture updateVenture(Venture venture);
    boolean deleteVenture (Long id);
    List<Venture> getAllVenture();
    Venture getVentureById(Long id);
}
