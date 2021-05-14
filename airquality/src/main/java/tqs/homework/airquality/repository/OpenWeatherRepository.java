package tqs.homework.airquality.repository;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import tqs.homework.airquality.model.AirData;
import tqs.homework.airquality.model.AirMetrics;

import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class OpenWeatherRepository implements IAirQualityRepository {
    private static final Logger logger = Logger.getLogger(OpenWeatherRepository.class.getName());
    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/air_pollution";
    private final String API_KEY = "b9632828cc8887780bb8e437a7c55f3a";
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public AirData getByCity(String city) {
         logger.log(Level.INFO, "Skipping call unsupported API query");
         return null;
    }
    @Override
    public AirData getByCoords(double lat, double lon) {
        ResponseEntity<String> response;
        try {
            response = restTemplate.getForEntity(BASE_URL
                    + "?lat=" + lat + "&lon=" + lon + "&appid=" + API_KEY, String.class);
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
            JsonNode root = mapper.readTree(response.getBody());
            airData = mapper.readValue(root.get("coord").toString(), AirData.class);

            JsonNode data = root.get("list");
            int size = data.size();

            AirMetrics[] metricsCollection = new AirMetrics[size];
            for (int i = 0; i < size; i++) {
                JsonNode d = data.get(i);
                AirMetrics metrics = mapper.readValue(d.get("components").toString(), AirMetrics.class);
                metrics.setUnixTime(d.get("dt").asLong());
                metrics.setAqi(convertAqi(d.get("main").get("aqi").asInt()));
                metricsCollection[i] = metrics;
            }
            airData.setMetricsCollection(metricsCollection);

        } catch (JsonProcessingException e) {
            logger.log(Level.WARNING, "Unexpected API response");
            e.printStackTrace();
        }
        return airData;
    }

    /**
     *
     * @param aqi
     * @return the converted aqi value in a 0-300 scale
     */
    private int convertAqi(int aqi) {
        return (aqi-1) * 50 + 25;
    }
}
