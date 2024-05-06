package tn.esprit.dima_maak.services;

import tn.esprit.dima_maak.entities.Loyalty;

import java.util.List;

public interface ILoyaltyService {
    public List<Loyalty> retrieveAllLoyalties();
    public Loyalty retrieveLoyalty(Long id);
    public Loyalty addLoyalty(Loyalty loyalty);
    public void removeLoyalty(Long id);
    public Loyalty modifyLoyalty(Loyalty loyalty);
}
