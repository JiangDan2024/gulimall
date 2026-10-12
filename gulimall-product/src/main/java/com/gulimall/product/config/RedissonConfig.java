package com.gulimall.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    @Bean(destroyMethod = "shutdown")
    RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer()
                // use "redis://" for Redis connection
                // use "valkey://" for Valkey connection
                // use "valkeys://" for Valkey SSL connection
                // use "rediss://" for Redis SSL connection
                .setAddress("redis://localhost:6379");

        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
