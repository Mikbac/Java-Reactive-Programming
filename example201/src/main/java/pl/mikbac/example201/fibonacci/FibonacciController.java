package pl.mikbac.example201.fibonacci;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 24.03.2026
 */

// localhost:8080/fibonacci/v1/6
@RestController
@RequestMapping("fibonacci/v1")
@RequiredArgsConstructor
public class FibonacciController {

    private final FibonacciService fibonacciService;
    private final FibonacciReactiveService fibonacciReactiveService;

    // curl localhost:8080/fibonacci/v1/40
    @GetMapping("{index}")
    public Mono<Integer> getFibonacci(@PathVariable int index) {
        return Mono.fromSupplier(() -> fibonacciService.getFibonacciNumber(index));
    }

    // curl -X DELETE localhost:8080/fibonacci/v1/40/cache
    @DeleteMapping("{index}/cache")
    public Mono<Void> evictFibonacci(@PathVariable int index) {
        return Mono.fromRunnable(() -> this.fibonacciService.evictCache(index));
    }

    // curl localhost:8080/fibonacci/v1/reactive/40
    @GetMapping("/reactive/{index}")
    public Mono<Integer> getReactiveFibonacci(@PathVariable int index) {
        return fibonacciReactiveService.getFibonacciNumberV1(index);
    }

}
