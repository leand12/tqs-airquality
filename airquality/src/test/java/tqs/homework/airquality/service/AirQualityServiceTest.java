package tqs.homework.airquality.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.homework.airquality.model.AirData;
import tqs.homework.airquality.repository.OpenWeatherRepository;
import tqs.homework.airquality.repository.WeatherBitRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class AirQualityServiceTest {

    @Mock(lenient=true)
    private WeatherBitRepository repository1;

    @Mock(lenient=true)
    private OpenWeatherRepository repository2;

    @InjectMocks
    private AirQualityService service;

    @BeforeEach
    public void setUp() {
        AirData cityRequest = new AirData("Aveiro");
        AirData coordsRequest = new AirData(50, 50);

        when( repository1.getByCoords(50, 50) ).thenReturn( coordsRequest );
        when( repository1.getByCity("Aveiro") ).thenReturn( cityRequest );
        when( repository1.getByCoords(-91, 181) ).thenReturn( null );
        when( repository1.getByCity("?") ).thenReturn( null );

        when( repository2.getByCoords(50, 50) ).thenReturn( coordsRequest );
        when( repository2.getByCity( any() ) ).thenReturn( null );
        when( repository2.getByCoords(-91, 181) ).thenReturn( null );
    }

    @Test
    public void whenGetByValidCoords_thenReturnValidRequest() {
        AirData coordsRequest = service.getByCoords(50, 50);

        assertThat(coordsRequest).isNotNull();
        assertThat(coordsRequest.getLat()).isEqualTo(50);
        assertThat(coordsRequest.getLon()).isEqualTo(50);
    }

    @Test
    public void whenGetByInvalidCoords_thenReturnNull() {
        assertThat(service.getByCoords(-91, 181)).isNull();
    }

    @Test
    public void whenGetByValidCity_thenReturnRequest() {
        AirData validCityRequest = service.getByCity("Aveiro");
        assertThat(validCityRequest).isNotNull();
        assertThat(validCityRequest.getCity()).isEqualTo("Aveiro");
    }

    @Test
    public void whenGetByInvalidCity_thenReturnNull() {
        AirData invalidCityRequest = service.getByCity("?");
        assertThat(invalidCityRequest).isNull();
    }
}

