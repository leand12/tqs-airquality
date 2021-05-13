package tqs.homework.airquality.repository;

import tqs.homework.airquality.model.AirData;

public interface IAirQualityRepository {
    AirData getByCoords(double lat, double lon);
    AirData getByCity(String city);
}
