package com.training.service;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.training.model.Greeting;
import com.training.util.AsyncResponse;

@Service
public class EmailServiceImpl implements EmailService {
	
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	public Boolean send(Greeting gretting) {
		LOG.info("> send");
		
		Boolean success = Boolean.FALSE;
		long pause = 5000;
		try{
			Thread.sleep(pause);
		} catch(Exception e){
			
		}
		LOG.info("Processing time was {} secs", pause / 1000);
		
		success = Boolean.TRUE;
		
		LOG.info("< send");
		return success;
	}

	@Async
	public void sendAsync(Greeting greeting) {
		LOG.info("> sendAsync");
		
		try{
			send(greeting);
		} catch(Exception e){
			LOG.warn("Exception caught while sending asynchronous mail", e);
		}
		
		LOG.info("< sendAsync");
	}
	
	@Async
	public AsyncResponse<Boolean> sendAsyncWithResult(Greeting greeting) {
		LOG.info("> sendAsyncWithResult");
		
		AsyncResponse<Boolean> response = new AsyncResponse<Boolean>();
		
		try{
			Boolean success = send(greeting);
			response.complete(success);
		} catch(Exception e){
			LOG.error("Exception caught while sending asynchronous mail", e);
			response.completeExceptionally(e);
		}
		
		LOG.info("< sendAsyncWithResult");
		return response;
	}

}
