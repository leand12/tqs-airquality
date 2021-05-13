package tqs.homework.airquality.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.homework.airquality.model.AirData;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class OpenWeatherRepositoryTest {

    @InjectMocks
    private OpenWeatherRepository repository;

    @Test
    public void whenGetByValidCoords_thenReturnValidAirData() {
        double lat = -90;
        double lon = 180;
        AirData airData = repository.getByCoords(lat, lon);

        assertThat(airData).isInstanceOf(AirData.class);
        assertThat(airData.getLat()).isEqualTo(lat);
        assertThat(airData.getLon()).isEqualTo(lon);
    }

    @Test
    public void whenGetByInvalidCoords_thenReturnNull() {
        assertThat(repository.getByCoords(-91, 181)).isNull();
    }

    @Test
    public void whenGetByCity_thenReturnNull() {
        assertThat(repository.getByCity("Beijing")).isNull();
        assertThat(repository.getByCity("?")).isNull();
    }
}
