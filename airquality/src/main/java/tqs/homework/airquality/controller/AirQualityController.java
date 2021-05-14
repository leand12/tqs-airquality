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
@CrossOrigin("http://localhost:3000")
public class AirQualityController {

    @Autowired
    private AirQualityService service;

    @GetMapping("/cache")
    public ResponseEntity<Cache> getCache() {
        var request = service.getCache();
        if (request != null)
            return new ResponseEntity<>(request, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/geo")
    public ResponseEntity<AirData> getAirDataByCoords(@RequestParam(value = "lat") double lat,
                                                      @RequestParam(value = "lon") double lon) {
        AirData request = service.getByCoords(lat, lon);
        if (request != null)
            return new ResponseEntity<>(request, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<AirData> getAirDataByCity(@PathVariable String city) {
        AirData request = service.getByCity(city);
        if (request != null)
            return new ResponseEntity<>(request, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
