package pl.mikbac.examples.example024;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by MikBac on 02.10.2025
 */

@Slf4j
public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        var client = new ServiceClient();

        log.info("starting");

            client.getPosts()
                    .subscribe(new SubscriberImpl());

            client.getPosts()
                    .subscribe(new SubscriberImpl());


        Thread.sleep(10000);
    }

}
