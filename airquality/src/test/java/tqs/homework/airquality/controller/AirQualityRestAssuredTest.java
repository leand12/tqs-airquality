package tqs.homework.airquality.controller;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import tqs.homework.airquality.caching.Cache;
import tqs.homework.airquality.model.AirData;
import tqs.homework.airquality.service.AirQualityService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@WebMvcTest(AirQualityController.class)
public class AirQualityRestAssuredTest {

    @Autowired
    private MockMvc servlet;

    @MockBean
    private AirQualityService service;

    @BeforeEach
    public void associateAssured() {
        RestAssuredMockMvc.mockMvc(servlet);
    }

    @Test
    public void whenGetCache_thenReturnCache() {
        when( service.getCache() ).thenReturn(new Cache(1));

        RestAssuredMockMvc.given().when()
                .get("/api/v1/cache")
                .then().assertThat().statusCode(200)
                .and().body("numRequests", equalTo(0))
                .and().body("numMisses", equalTo(0))
                .and().body("numHits", equalTo(0));

        verify(service, times(1)).getCache();
    }

    @Test
    public void whenGetByCoords_thenReturnAirData() {
        when( service.getByCoords( anyDouble(), anyDouble() ) ).thenReturn(
                new AirData(50, 50));

        RestAssuredMockMvc.given().when()
                .get("/api/v1/geo?lat=50&lon=50")
                .then().assertThat().statusCode(200)
                .and().body("lat", equalTo(50.0f))
                .and().body("lon", equalTo(50.0f));

        verify(service, times(1)).getByCoords(anyDouble(), anyDouble());
    }

    @Test
    public void whenGetByCity_thenReturnAirData() {
        when( service.getByCity( anyString() ) ).thenReturn( new AirData("Aveiro") );

        RestAssuredMockMvc.given().when()
                .get("/api/v1/city/Aveiro")
                .then().assertThat().statusCode(200)
                .and().body("city_name", equalTo("Aveiro"));

        verify(service, times(1)).getByCity(anyString());
    }
}