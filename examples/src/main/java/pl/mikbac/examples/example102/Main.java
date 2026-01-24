package pl.mikbac.examples.example102;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.reactor.netty.NettyReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;

/**
 * Created by MikBac on 11.10.2025
 */

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(pl.mikbac.examples.example102.Main.class, args);
    }

    @Bean
    public NettyReactiveWebServerFactory nettyFactory() {
        NettyReactiveWebServerFactory factory =
                new NettyReactiveWebServerFactory();
        factory.setPort(8085);
        return factory;
    }

}
