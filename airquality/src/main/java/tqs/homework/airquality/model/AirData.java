package tqs.homework.airquality.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class AirData {
    @JsonProperty("city_name") String city;
    @JsonProperty("lat") double lat;
    @JsonProperty("lon") double lon;
    @JsonProperty("data") AirMetrics[] metricsCollection;

    public AirData(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public AirData(String city) {
        this.city = city;
    }
}
