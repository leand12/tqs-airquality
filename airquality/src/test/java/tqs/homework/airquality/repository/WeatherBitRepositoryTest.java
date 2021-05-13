package tqs.homework.airquality.repository;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.homework.airquality.model.AirData;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class WeatherBitRepositoryTest {

    @InjectMocks
    private WeatherBitRepository repository;

    @Test
    public void whenGetByCoords_thenReturnAirData() {
        assertThat(repository.getByCoords(-90, 180)).isInstanceOf(AirData.class);
        assertThat(repository.getByCoords(-91, 181)).isInstanceOf(AirData.class);
    }

    @Test
    public void whenGetByValidCity_thenReturnValidAirData() {
        String city = "Beijing";
        AirData airData = repository.getByCity(city);

        assertThat(airData).isInstanceOf(AirData.class);
        assertThat(airData.getCity()).isEqualTo(city);
    }

    @Test
    public void whenGetByInvalidCity_thenReturnNull() {
        assertThat(repository.getByCity("?")).isNull();
    }
}
