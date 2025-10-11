package pl.mikbac.examples.example011;

import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 04.10.2025
 */
public interface FileService {

    Mono<String> read(String fileName);

    Mono<Void> write(String fileName, String content);

    Mono<Void> delete(String fileName);

}
