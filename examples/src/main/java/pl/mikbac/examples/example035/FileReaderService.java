package pl.mikbac.examples.example035;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Created by MikBac on 16.10.2025
 */

@Slf4j
public class FileReaderService {

    public Flux<String> read(final Path path) {
        return Flux.generate(
                () -> openFile(path),
                this::readFile,
                this::closeFile
        );
    }

    private BufferedReader openFile(final Path path) throws IOException {
        log.info("open file");
        return Files.newBufferedReader(path);
    }

    private BufferedReader readFile(final BufferedReader reader, final SynchronousSink<String> sink) {
        try {
            var line = reader.readLine();
            if (Objects.isNull(line)) {
                sink.complete();
            } else {
                log.info("read next line");
                sink.next(line);
            }
        } catch (Exception e) {
            sink.error(e);
        }
        return reader;
    }

    private void closeFile(final BufferedReader reader) {
        try {
            log.info("close file");
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
