package com.training.controller;

import java.math.BigInteger;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.model.Greeting;
import com.training.service.EmailService;
import com.training.service.GreetingService;
import com.training.util.AsyncResponse;

@RestController
public class GreetingController {
	
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GreetingService greetingService;
	@Autowired
	private EmailService emailService;
	
	@RequestMapping(
			value="/greetings", 
			method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Greeting>> getGreetings(){
		
		Collection<Greeting> greetings = greetingService.findAll();
		
		return new ResponseEntity<Collection<Greeting>> (greetings, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/greetings/{id}", 
			method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> getGreeting(@PathVariable("id") BigInteger id) {
		
		Greeting greeting = greetingService.findOne(id.longValue());
		
		if(greeting == null) {
			return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Greeting> (greeting, HttpStatus.OK);
		}
	}
	
	@RequestMapping(
			value = "/greetings", 
			method=RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting) {
		
		Greeting savedGreeting = greetingService.create(greeting);
		
		return new ResponseEntity<Greeting> (savedGreeting, HttpStatus.CREATED);
	}
	
	@RequestMapping(
			value = "/greetings/{id}", 
			method=RequestMethod.PUT, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> updateGreeting(@RequestBody Greeting greeting){
		Greeting updatedGreeting = greetingService.update(greeting);
		if(updatedGreeting == null){
			return new ResponseEntity<Greeting> (HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Greeting> (updatedGreeting, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/greetings/{id}", 
			method = RequestMethod.DELETE, 
			consumes = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<Greeting> deleteGreeting(@PathVariable("id") BigInteger id, @RequestBody Greeting greeting) {
		boolean deleted = greetingService.delete(id.longValue());
		
		if(!deleted) {
			return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(
			value = "/greetings/{id}/send",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> sendGreeting(
			@PathVariable("id") Long id, 
			@RequestParam(value = "wait", defaultValue = "false") boolean waitForAsyncResult){
		
		LOG.info("> sendGreeting");
		
		Greeting greeting = null;
		try {
			greeting = greetingService.findOne(id);
			if(null == greeting) {
				LOG.info("< sendGreeting");
				return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
			}
			
			if(waitForAsyncResult){
				AsyncResponse<Boolean> asyncResponse = emailService.sendAsyncWithResult(greeting);
				boolean emailSent = asyncResponse.get();
				LOG.info("email sent? {}", emailSent);
			} else {
				emailService.sendAsync(greeting);
			}
		} catch (Exception e){
			LOG.error("A problem occurred while sending greeting", e);
		}
		
		LOG.info("< sendGreeting");
		
		return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
	}
}
