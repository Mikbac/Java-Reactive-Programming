package pl.mikbac.examples.example010;

import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.LoopResources;

/**
 * Created by MikBac on 04.10.2025
 */
public class ServiceClient {

    private static final String BASE_URL = "https://api.nbp.pl";
    private final HttpClient httpClient;

    public ServiceClient() {
        // prefix – thread name prefix ("nbp" → threads will be named like nbp-epoll-1).
        // nThreads – number of event loop threads.
        // daemon – whether the created threads are daemon threads
        var loopResources = LoopResources.create("nbp", 1, true);
        this.httpClient = HttpClient.create().runOn(loopResources).baseUrl(BASE_URL);
    }

    public Mono<String> getExchangeRates(final String currency) {
        return this.httpClient.get()
                .uri("/api/exchangerates/rates/A/" + currency)
                .responseContent()
                .asString()
                .next();
    }

}
