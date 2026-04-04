package pl.mikbac.example200;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by MikBac on 16.03.2026
 */

@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379");
//        config.setUsername("user");
//        config.setPassword("pass");
        return Redisson.create(config);
    }

    @Bean
    public RedissonReactiveClient redissonReactiveClient() {
        return redissonClient().reactive();
    }

}
