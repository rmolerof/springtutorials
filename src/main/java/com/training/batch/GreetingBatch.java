package com.training.batch;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.training.model.Greeting;
import com.training.service.GreetingService;

@Profile("batch")
@Component
public class GreetingBatch {

	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GreetingService greetingService;
	
	@Scheduled(cron = "${batch.greeting.cron}")
	public void cronJob() {
		LOG.info("> cronJob");
		
		Collection<Greeting> greetings = greetingService.findAll();
		LOG.info("There are {} greetings in the data source", greetings.size());
		
		LOG.info("< cronJob");
	}
	
	@Scheduled(
			initialDelayString = "${batch.greeting.initialdelay}",
			fixedRateString = "${batch.greeting.fixedrate}")
	public void fixedRateWithInitialDelay (){
		LOG.info("> fixedRateWithInitialDelay");
		
		long pause = 5000;
		long start = System.currentTimeMillis();
		do {
			if(start + pause < System.currentTimeMillis()) {
				break;
			}
		} while (true);
		LOG.info("Processing time was {} secs", pause/1000);
		
		LOG.info("< fixedRateWithInitialDelay");
	}
	
	/*
	 * Ensures that at least one instance of the process will be running at
	 * any given time
	 */
	@Scheduled(
			initialDelayString="${batch.greeting.initialdelay}",
			fixedDelayString="${batch.greeting.fixeddelay}")
	public void fixedDelayWithInitialDelay (){
		LOG.info("> fixedDelayWithInitialDelay");
		
		long pause = 5000;
		long start = System.currentTimeMillis();
		do {
			if(start + pause < System.currentTimeMillis()) {
				break;
			}
		} while (true);
		LOG.info("Processing time was {} secs", pause/1000);
		
		LOG.info("< fixedDelayWithInitialDelay");
	}
}
