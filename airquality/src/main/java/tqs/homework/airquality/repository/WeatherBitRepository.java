package tqs.homework.airquality.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import tqs.homework.airquality.model.AirData;

import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class WeatherBitRepository implements IAirQualityRepository {
    private static final Logger logger = Logger.getLogger(WeatherBitRepository.class.getName());
    private final String BASE_URL = "https://api.weatherbit.io/v2.0/";
    private final String API_KEY = "1f123a42ce244e258ee60f3911e4518e";
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public AirData getByCoords(double lat, double lon) {
        ResponseEntity<String> response;

        if (lat < -90 || lat > 90 || lon < -180 || lon > 180)
            return null;

        try {
            response = restTemplate.getForEntity(BASE_URL
                + "current/airquality?lat=" + lat + "&lon=" + lon + "&key=" + API_KEY, String.class);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Invalid API request: " + e);
            return null;
        }
        return processResponse(response);
    }

    @Override
    public AirData getByCity(String city) {
        ResponseEntity<String> response;
        try {
            response = restTemplate.getForEntity(BASE_URL
                    + "current/airquality?city=" + city + "&key=" + API_KEY, String.class);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Invalid API request: " + e);
            return null;
        }
        return processResponse(response);
    }

    private AirData processResponse(ResponseEntity<String> response) {
        if (response.getBody() == null)
            return null;

        AirData airData = null;
        var mapper = new ObjectMapper();

        try {
            airData = mapper.readValue(response.getBody(), AirData.class);
        } catch (JsonProcessingException e) {
            logger.log(Level.WARNING, "Unexpected API response");
            e.printStackTrace();
        }
        return airData;
    }
}
