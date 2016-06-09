package com.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class Application {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CacheManager cacheManager(){
		/*ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager("greetings");*/
		GuavaCacheManager cacheManager = new GuavaCacheManager("greetings");
		return cacheManager;		
	}
}
