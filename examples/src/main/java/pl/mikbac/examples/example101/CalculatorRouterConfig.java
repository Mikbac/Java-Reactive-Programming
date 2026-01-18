package pl.mikbac.examples.example101;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.function.BiFunction;

/**
 * Created by MikBac on 18.01.2026
 */

@Configuration
@RequiredArgsConstructor
public class CalculatorRouterConfig {

    @Bean
    public RouterFunction<ServerResponse> calculator() {
        return RouterFunctions.route()
                .path("calculator/v1", this::calculatorRoutes)
                .build();
    }

    private RouterFunction<ServerResponse> calculatorRoutes() {
        return RouterFunctions.route()
                .GET("/{a}/0", req -> ServerResponse.badRequest().bodyValue("Param b cannot be 0"))
                .GET("/{a}/{b}", isOperation("+"), handle((a, b) -> a + b))
                .GET("/{a}/{b}", isOperation("-"), handle((a, b) -> a - b))
                .GET("/{a}/{b}", isOperation("*"), handle((a, b) -> a * b))
                .GET("/{a}/{b}", isOperation("/"), handle((a, b) -> a / b))
                .GET("/{a}/{b}", req -> ServerResponse.badRequest().bodyValue("X-Operation header is missing or invalid"))
                .build();
    }

    private RequestPredicate isOperation(String operation) {
        return RequestPredicates.headers(h -> operation.equals(h.firstHeader("X-Operation")));
    }

    private HandlerFunction<ServerResponse> handle(BiFunction<Integer, Integer, Integer> function) {
        return req -> {
            int a = Integer.parseInt(req.pathVariable("a"));
            int b = Integer.parseInt(req.pathVariable("b"));
            var result = function.apply(a, b);
            return ServerResponse.ok().bodyValue(result);
        };
    }

}
