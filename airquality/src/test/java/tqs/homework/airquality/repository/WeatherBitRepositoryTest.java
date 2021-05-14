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
    public void whenGetByValidCoords_thenReturnAirData() {
        double lat = -90;
        double lon = 180;
        AirData airData = repository.getByCoords(lat, lon);

        assertThat(airData).isInstanceOf(AirData.class);
        assertThat(airData.getLat()).isEqualTo(lat);
        assertThat(airData.getLon()).isEqualTo(lon);
    }

    @Test
    public void whenGetByInvalidCoords_thenReturnAirData() {
        assertThat(repository.getByCoords(-91, 181)).isNull();
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
