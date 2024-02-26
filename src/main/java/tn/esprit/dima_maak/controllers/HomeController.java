package tn.esprit.dima_maak.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "home test";
    }

    @GetMapping("/secured")
    public String secured(){
        return "secured test";
    }
}
