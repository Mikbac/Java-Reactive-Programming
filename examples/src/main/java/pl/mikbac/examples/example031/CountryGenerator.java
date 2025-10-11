package pl.mikbac.examples.example031;

import com.github.javafaker.Faker;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

/**
 * Created by MikBac on 8.10.2025
 */

public class CountryGenerator implements Consumer<FluxSink<String>> {

    private final static Faker faker = Faker.instance();
    private FluxSink<String> sink;

    @Override
    public void accept(FluxSink<String> stringFluxSink) {
        this.sink = stringFluxSink;
    }

    public void generate(){
        this.sink.next(faker.country().name());
    }

    public void complete(){
        this.sink.complete();
    }

}
