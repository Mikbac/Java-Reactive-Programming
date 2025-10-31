package pl.mikbac.examples.example030;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by MikBac on 8.10.2025
 */
@Slf4j
public class Main {

    public static void main(String[] args) {

        arrayListDemo(); // NOT thread safe
        // ArrayList size: 3712
        copyOnWriteArrayListDemo(); // thread safe
        // CopyOnWriteArrayList size: 10000
        sinkDemo(); // thread safe
        // ArrayList (via sink) size: 10000

    }

    @SneakyThrows
    private static void arrayListDemo() {
        var list = new ArrayList<Integer>();
        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        };
        for (int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(runnable);
        }
        Thread.sleep(4000);
        log.info("ArrayList size: {}", list.size());
    }

    @SneakyThrows
    private static void copyOnWriteArrayListDemo() {
        var list = new CopyOnWriteArrayList<Integer>();
        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        };
        for (int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(runnable);
        }
        Thread.sleep(4000);
        log.info("CopyOnWriteArrayList size: {}", list.size());
    }

    @SneakyThrows
    private static void sinkDemo() {
        var list = new ArrayList<String>();
        var generator = new CountryGenerator();
        var flux = Flux.create(generator);
        flux.subscribe(list::add);

        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                generator.generate();
            }
        };
        for (int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(runnable);
        }
        Thread.sleep(4000);
        log.info("ArrayList (via sink) size: {}", list.size());
    }

}
