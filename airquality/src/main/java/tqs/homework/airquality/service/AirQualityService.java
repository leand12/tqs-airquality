package tqs.homework.airquality.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.homework.airquality.caching.Cache;
import tqs.homework.airquality.model.AirData;
import tqs.homework.airquality.repository.WeatherBitRepository;
import tqs.homework.airquality.repository.OpenWeatherRepository;

@Service
public class AirQualityService {
    private final Cache cache = new Cache(60);

    @Autowired
    private WeatherBitRepository repository1;

    @Autowired
    private OpenWeatherRepository repository2;

    public Cache getCache() {
        return cache;
    }

    public AirData getByCoords(double lat, double lon) {
        String key = "getByCoords(" + lat + "," + lon +  ")";
        AirData request = cache.getCachedRequest(key);

        if (request != null)
            return request;

        request = repository1.getByCoords(lat, lon);
        if (request == null)
            request = repository2.getByCoords(lat, lon);

        cache.cacheRequest(key, request);
        return request;
    }

    public AirData getByCity(String city) {
        String key = "getByCity(" + city + ")";
        AirData request = cache.getCachedRequest(key);

        if (request != null)
            return request;

        request = repository1.getByCity(city);
        if (request == null)
            request = repository2.getByCity(city);

        cache.cacheRequest(key, request);
        return request;
    }
}
