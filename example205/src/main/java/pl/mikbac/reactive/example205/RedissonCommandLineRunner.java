package pl.mikbac.reactive.example205;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RGeoReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.geo.GeoSearchArgs;
import org.redisson.api.geo.GeoUnit;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * Created by MikBac on 05.04.2026
 */

@Service
@RequiredArgsConstructor
public class RedissonCommandLineRunner implements CommandLineRunner {

    private final RedissonReactiveClient redissonReactiveClient;

    @Override
    public void run(final String... args) throws Exception {
//        runGeoLocationExample();
        runGeoLocationMapExample();

    }

    private void runGeoLocationExample() {
        RGeoReactive<LocationModel> geo = redissonReactiveClient.getGeo("locations", new TypedJsonJacksonCodec(LocationModel.class));

        geo.add(10.0, 20.0, new LocationModel("l1", 10.0, 20.0))
                .then(geo.add(30.0, 40.0, new LocationModel("l2", 30.0, 40.0)))
                .then(geo.add(50.0, 60.0, new LocationModel("l3", 50.0, 60.0)))
                .then(geo.search(GeoSearchArgs.from(35.0, 35.0).radius(2000.0, GeoUnit.KILOMETERS)))
                .doOnNext(location -> System.out.println("Found location: " + location))
                .subscribe();
    }

    private void runGeoLocationMapExample() {
        RMapReactive<String, LocationModel> map = redissonReactiveClient.getMap("locations:pl", new TypedJsonJacksonCodec(String.class, LocationModel.class));

        map.put("zip-1", new LocationModel("l1", 10.0, 20.0))
                .then(map.put("zip-2", new LocationModel("l2", 30.0, 40.0)))
                .then(map.put("zip-3", new LocationModel("l3", 50.0, 60.0)))
                .then(map.get("zip-2"))
                .doOnNext(location -> System.out.println("Found location: " + location))
                .subscribe();
    }

}
