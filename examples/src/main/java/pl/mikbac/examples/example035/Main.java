package pl.mikbac.examples.example035;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;

/**
 * Created by MikBac on 12.10.2025
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        var path = Path.of("examples/src/main/resources/long_text.txt");
        var fileReaderService = new FileReaderService();
        fileReaderService.read(path)
                .takeUntil(s -> s.contains("end-read"))
                .subscribe(new SubscriberImpl());
    }

}
