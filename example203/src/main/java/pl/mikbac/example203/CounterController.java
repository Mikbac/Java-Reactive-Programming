package pl.mikbac.example203;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

/**
 * Created by MikBac on 30.03.2026
 */

@RestController
@RequestMapping("counter/v1")
@RequiredArgsConstructor
public class CounterController {

    private final CounterService counterService;

    // http://localhost:8080/counter/v1
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Map<Integer, Double>> getCounter() {
        return counterService.getCounterStats(5)
                .repeatWhen(l -> Flux.interval(Duration.ofSeconds(3)));
    }

}
