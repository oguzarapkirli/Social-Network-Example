package com.oguzarapkirli.socialnetworkexample.config;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {

    @Bean
    public RateLimiterConfig rateLimiterConfig() {
        // 1 request per 5 seconds
        return RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofSeconds(100))
                .limitForPeriod(100)
                .timeoutDuration(Duration.ofSeconds(0))
                .build();
    }
    @Bean
    public RateLimiterRegistry rateLimiterRegistry(RateLimiterConfig rateLimiterConfig) {
        return RateLimiterRegistry.of(rateLimiterConfig);
    }
}