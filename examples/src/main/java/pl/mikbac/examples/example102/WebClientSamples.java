package pl.mikbac.examples.example102;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.function.Consumer;

/**
 * Created by MikBac on 18.01.2026
 */

@Service
@Slf4j
public class WebClientSamples implements CommandLineRunner {

    @Override
    public void run(final String... args) throws Exception {

    }


    // Helper methods

    private WebClient createWebClient() {
        return createWebClient(b -> {});
    }

    private WebClient createWebClient(Consumer<WebClient.Builder> consumer) {
        var builder = WebClient.builder()
                .baseUrl("http://localhost:8080/employees/v1");
        consumer.accept(builder);
        return builder.build();
    }


}
