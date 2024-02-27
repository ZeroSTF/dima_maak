package tn.esprit.dima_maak.services;

import tn.esprit.dima_maak.entities.Venture;

public interface IVentureServices {

    Venture addVenture(Venture venture);
    Venture updateVenture(Venture venture);
    Void deleteVenture (Long id);
    Venture getVentureById(Long id);
}
