package com.crossover.techtrial;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author crossover
 *
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class CrossRideApplication {
	public static void main(String[] args) {
		SpringApplication.run(CrossRideApplication.class, args);
	}
	
	@Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
          new ConcurrentMapCache("person"), 
          new ConcurrentMapCache("ride")));
        return cacheManager;
    }
}
