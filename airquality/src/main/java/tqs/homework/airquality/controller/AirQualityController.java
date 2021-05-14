package tqs.homework.airquality.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.homework.airquality.caching.Cache;
import tqs.homework.airquality.model.AirData;
import tqs.homework.airquality.service.AirQualityService;


@RestController
@RequestMapping("/api/v1/")
@CrossOrigin("*")
public class AirQualityController {

    @Autowired
    private AirQualityService service;

    @GetMapping("/cache")
    public ResponseEntity<Cache> getCache() {
        Cache request = service.getCache();
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @GetMapping("/geo")
    public ResponseEntity<AirData> getAirDataByCoords(@RequestParam(value = "lat") double lat,
                                               @RequestParam(value = "lon") double lon) {
        AirData request = service.getByCoords(lat, lon);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @GetMapping("/city/{city}")
    private ResponseEntity<AirData> getAirDataByCity(@PathVariable String city) {
        AirData request = service.getByCity(city);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }
}
