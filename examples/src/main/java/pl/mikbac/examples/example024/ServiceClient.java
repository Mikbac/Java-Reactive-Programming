package pl.mikbac.examples.example024;

import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.LoopResources;

/**
 * Created by MikBac on 04.10.2025
 */
public class ServiceClient {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final HttpClient httpClient;

    public ServiceClient() {
        // prefix – thread name prefix ("nbp" → threads will be named like nbp-epoll-1).
        // nThreads – number of event loop threads.
        // daemon – whether the created threads are daemon threads
        var loopResources = LoopResources.create("nbp", 1, true);
        this.httpClient = HttpClient.create().runOn(loopResources).baseUrl(BASE_URL);
    }

    public Flux<String> getPosts() {
        return this.httpClient.get()
                .uri("/posts")
                .responseContent()
                .asString();
    }

}
