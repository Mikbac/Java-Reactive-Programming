package pl.mikbac.examples.example011;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by MikBac on 04.10.2025
 */

@Slf4j
public class FileServiceImpl implements FileService {

    private static final Path PATH = Path.of("examples/src/main/resources/");

    @Override
    public Mono<String> read(String fileName) {
        return Mono.fromCallable(() -> Files.readString(PATH.resolve(fileName)));
    }

    @Override
    public Mono<Void> write(String fileName, String content) {
        return Mono.fromRunnable(() -> this.writeFile(fileName, content));
    }

    @Override
    public Mono<Void> delete(String fileName) {
        return Mono.fromRunnable(() -> this.deleteFile(fileName));
    }

    @SneakyThrows
    private void writeFile(String fileName, String content) {
        Files.writeString(PATH.resolve(fileName), content);
        log.info("created {}", fileName);
    }

    @SneakyThrows
    private void deleteFile(String fileName) {
        Files.delete(PATH.resolve(fileName));
        log.info("deleted {}", fileName);
    }

}
