package pl.mikbac.examples.example011;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by MikBac on 02.10.2025
 */

@Slf4j
public class Main {

    public static void main(String[] args) {
        var fileService = new FileServiceImpl();

        fileService.write("test.txt", "aaaaaaaaaaaaaaa")
                .subscribe(new SubscriberImpl());

        fileService.read("test.txt")
                .subscribe(new SubscriberImpl());

        fileService.delete("test.txt")
                .subscribe(new SubscriberImpl());
    }

}
