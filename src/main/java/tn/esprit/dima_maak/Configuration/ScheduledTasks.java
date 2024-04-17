package tn.esprit.dima_maak.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tn.esprit.dima_maak.serviceimpl.VentureServicesImpl;
@Component
public class ScheduledTasks {
    @Autowired
    private VentureServicesImpl ventureService;

    //@Scheduled(fixedRate = 300000) // 2 minutes
    public void deleteExpiredVentures() {
        ventureService.deleteVenturesExpired();
    }

}
