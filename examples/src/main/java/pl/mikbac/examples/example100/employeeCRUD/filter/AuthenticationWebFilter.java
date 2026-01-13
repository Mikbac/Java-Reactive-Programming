package pl.mikbac.examples.example100.employeeCRUD.filter;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 11.01.2026
 */

@Order(1)
@Service
@RequiredArgsConstructor
public class AuthenticationWebFilter implements WebFilter {

    private final AuthService authService;

    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
       final String token = exchange.getRequest().getHeaders().getFirst("user-token");
        if (StringUtils.isNotBlank(token) && authService.isTokenCorrect(token)) {
            exchange.getAttributes().put("role", authService.getRole(token));
            return chain.filter(exchange);
        }

        return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));
    }


}
