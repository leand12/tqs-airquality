package tqs.homework.airquality.caching;

import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tqs.homework.airquality.model.AirData;

import static org.assertj.core.api.Assertions.assertThat;


public class CacheTest {
    private Cache cache;
    private AirData request;

    @BeforeEach
    private void setUp() {
        request = new AirData();
        cache = new Cache(1);
    }

    @Test
    public void whenNoCachedRequest_thenCacheRequest_andIncNumMisses() {
        assertThat(cache.getCachedRequest("?")).isNull();
        assertThat(cache.getNumRequests()).isEqualTo(1);
        assertThat(cache.getNumMisses()).isEqualTo(1);
        assertThat(cache.getNumHits()).isEqualTo(0);
    }

    @Test
    public void whenExpiredCachedRequest_thenIncNumMisses() throws InterruptedException {
        String key = "key";
        cache.cacheRequest(key, request);
        Thread.sleep(2000);
//        Awaitility.setDefaultTimeout(Duration.FIVE_SECONDS);

        assertThat(cache.getCachedRequest(key)).isNull();
        assertThat(cache.getNumRequests()).isEqualTo(1);
        assertThat(cache.getNumMisses()).isEqualTo(1);
        assertThat(cache.getNumHits()).isEqualTo(0);
    }

    @Test
    public void whenCachedRequest_thenReturnRequest_andIncNumHits() {
        String key = "key";
        cache.cacheRequest(key, request);

        assertThat(cache.getCachedRequest(key)).isEqualTo(request);
        assertThat(cache.getNumRequests()).isEqualTo(1);
        assertThat(cache.getNumMisses()).isEqualTo(0);
        assertThat(cache.getNumHits()).isEqualTo(1);
    }
}
