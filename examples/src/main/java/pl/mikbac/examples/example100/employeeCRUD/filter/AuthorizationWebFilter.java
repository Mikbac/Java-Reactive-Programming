package pl.mikbac.examples.example100.employeeCRUD.filter;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 11.01.2026
 */

@Order(2)
@Service
public class AuthorizationWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
        final UserRole role = exchange.getAttributeOrDefault("role", UserRole.BASIC);

        return switch (role) {
            case BASIC -> HttpMethod.GET.equals(exchange.getRequest().getMethod()) ?
                    chain.filter(exchange) :
                    Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN));
            case ADMIN -> chain.filter(exchange);
        };
    }

}
