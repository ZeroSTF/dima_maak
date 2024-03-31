package tn.esprit.dima_maak.services;

import org.springframework.security.core.Authentication;

public interface ITokenService {
    public String generateJwt(Authentication auth);
}
