package tqs.homework.airquality.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AirQualityControllerIT {

    @Autowired
    private MockMvc servlet;

    @Test
    @Order(1)
    public void whenGetCacheBeforeRequests_thenReturnValidCache() throws Exception {
        servlet.perform( MockMvcRequestBuilders.get("/api/v1/cache")
                .contentType(MediaType.APPLICATION_JSON) )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("numRequests").value(0))
                .andExpect(jsonPath("numMisses").value(0))
                .andExpect(jsonPath("numHits").value(0));
    }

    @Test
    @Order(2)
    public void whenGetCacheAfterSomeRequests_thenReturnValidCache() throws Exception {
        servlet.perform( MockMvcRequestBuilders.get("/api/v1/city/Aveiro") );
        servlet.perform( MockMvcRequestBuilders.get("/api/v1/geo?lat=0&lon=0") );
        servlet.perform( MockMvcRequestBuilders.get("/api/v1/city/Aveiro") );
        servlet.perform( MockMvcRequestBuilders.get("/api/v1/geo?lat=0&lon=0") );
        servlet.perform( MockMvcRequestBuilders.get("/api/v1/city/Porto") );
        servlet.perform( MockMvcRequestBuilders.get("/api/v1/geo?lat=10&lon=10") );

        servlet.perform( MockMvcRequestBuilders.get("/api/v1/cache")
                .contentType(MediaType.APPLICATION_JSON) )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("numRequests").value(6))
                .andExpect(jsonPath("numMisses").value(4))
                .andExpect(jsonPath("numHits").value(2));
    }

    @Test
    @Order(3)
    public void whenGetByValidCoords_thenReturnValidAirData() throws Exception {
        servlet.perform( MockMvcRequestBuilders.get("/api/v1/geo?lat=50&lon=50")
                .contentType(MediaType.APPLICATION_JSON) )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("lat").value(50))
                .andExpect(jsonPath("lon").value(50));
    }

    @Test
    @Order(4)
    public void whenGetByInvalidCoords_thenReturnCorrectStatus() throws Exception {
        servlet.perform( MockMvcRequestBuilders.get("/api/v1/geo?lat=-91&lon=181")
                .contentType(MediaType.APPLICATION_JSON) )
                .andDo(print())
                .andExpect(status().isNotFound());

        servlet.perform( MockMvcRequestBuilders.get("/api/v1/geo?lat=lat&lon=lon")
                .contentType(MediaType.APPLICATION_JSON) )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    public void whenGetByValidCity_thenReturnValidAirData() throws Exception {
        servlet.perform( MockMvcRequestBuilders.get("/api/v1/city/Aveiro")
                .contentType(MediaType.APPLICATION_JSON) )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("city_name").value("Aveiro"));
    }

    @Test
    @Order(6)
    public void whenGetByInvalidCity_thenReturnNotFound() throws Exception {
        servlet.perform( MockMvcRequestBuilders.get("/api/v1/city/c")
                .contentType(MediaType.APPLICATION_JSON) )
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
