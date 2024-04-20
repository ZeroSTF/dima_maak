package tn.esprit.dima_maak.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Weather {

    private Long id;
    private String name;
    private List<WeatherDescription> weather;
    private Map<String, Object> main;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WeatherDescription> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherDescription> weather) {
        this.weather = weather;
    }

    public Map<String, Object> getMain() {
        return main;
    }

    public void setMain(Map<String, Object> main) {
        this.main = main;
        // Convert temperature from Kelvin to Celsius
        if (main.containsKey("temp")) {
            double tempInKelvin = (double) main.get("temp");
            double tempInCelsius = tempInKelvin - 273.15;
            double tempInKelvinmin = (double) main.get("temp_min");
            double tempInCelsiusmin = tempInKelvinmin - 273.15;
            double tempInKelvinmax = (double) main.get("temp_max");
            double tempInCelsiusmax= tempInKelvinmax - 273.15;
            main.put("temp", tempInCelsius);
            main.put("temp_min", tempInCelsiusmin);
            main.put("temp_max", tempInCelsiusmax);
        }
    }

    static class WeatherDescription {
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
