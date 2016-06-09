package com.training.batch;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.training.model.Greeting;
import com.training.service.GreetingService;

@Component
public class GreetingBatch {

	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GreetingService greetingService;
	
	/*@Scheduled(cron = "0,30 * * * * *")*/
	public void cronJob() {
		LOG.info("> cronJob");
		
		Collection<Greeting> greetings = greetingService.findAll();
		LOG.info("There are {} greetings in the data source", greetings.size());
		
		LOG.info("< cronJob");
	}
	
	/*@Scheduled(
			initialDelay = 5000,
			fixedRate = 15000)*/
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
			initialDelay=5000,
			fixedDelay=15000)
	public void fixedDelayWithInitialDelay (){
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
}
