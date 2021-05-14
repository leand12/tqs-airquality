package tqs.homework.airquality.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tqs.homework.airquality.caching.Cache;
import tqs.homework.airquality.model.AirData;
import tqs.homework.airquality.service.AirQualityService;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AirQualityController.class)
public class AirQualityControllerTest {

    @Autowired
    MockMvc servlet;

    @MockBean
    AirQualityService service;

    @Test
    public void whenGetCache_thenReturnCache() throws Exception {
        when( service.getCache() ).thenReturn(new Cache(1));

        servlet.perform( MockMvcRequestBuilders.get("/api/v1/cache")
                .contentType(MediaType.APPLICATION_JSON) )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("numRequests").value(0))
                .andExpect(jsonPath("numMisses").value(0))
                .andExpect(jsonPath("numHits").value(0));
    }

    @Test
    public void whenGetByValidCoords_thenReturnValidAirData() throws Exception {
        when( service.getByCoords( anyDouble(), anyDouble() ) ).thenReturn(
                new AirData(50, 50));

        servlet.perform( MockMvcRequestBuilders.get("/api/v1/geo?lat=50&lon=50")
                .contentType(MediaType.APPLICATION_JSON) )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("lat").value(50))
                .andExpect(jsonPath("lon").value(50));
    }

    @Test
    public void whenGetByInvalidCoords_thenReturnNotFound() throws Exception {
        when( service.getByCoords( anyDouble(), anyDouble() ) ).thenReturn( null );

        servlet.perform( MockMvcRequestBuilders.get("/api/v1/geo?lat=50&lon=50")
                .contentType(MediaType.APPLICATION_JSON) )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenGetByValidCity_thenReturnValidAirData() throws Exception {
        when( service.getByCity( anyString() ) ).thenReturn( new AirData("Aveiro") );

        servlet.perform( MockMvcRequestBuilders.get("/api/v1/city/Aveiro")
                .contentType(MediaType.APPLICATION_JSON) )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("city_name").value("Aveiro"));
    }

    @Test
    public void whenGetByInvalidCity_thenReturnNotFound() throws Exception {
        when( service.getByCity( anyString() ) ).thenReturn( null );

        servlet.perform( MockMvcRequestBuilders.get("/api/v1/city/Aveiro")
                .contentType(MediaType.APPLICATION_JSON) )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
