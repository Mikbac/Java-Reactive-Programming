package pl.mikbac.examples.example010;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by MikBac on 02.10.2025
 */

@Slf4j
public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        var client = new ServiceClient();

        log.info("starting");
        final List<String> currencies = List.of("USD", "EUR", "GBP", "THB", "CHF", "AUD", "CNY", "NZD", "HUF", "JPY");
        for (String currency : currencies) {
            client.getExchangeRates(currency)
                    .subscribe(new SubscriberImpl());
        }


        Thread.sleep(6000);
    }

}
