package com.training.controller;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.model.Greeting;

@RestController
public class GreetingController {
	
	private static BigInteger nextId;
	private static Map<BigInteger, Greeting> greetingMap;
	
	private static Greeting save(Greeting greeting) {
		if(greetingMap == null) {
			greetingMap = new HashMap<BigInteger, Greeting>();
			nextId = BigInteger.ONE;
		}
		greeting.setId(nextId);
		nextId = nextId.add(BigInteger.ONE);
		greetingMap.put(greeting.getId(), greeting);
		return greeting;
	}
	
	static {
		Greeting g1 = new Greeting();
		g1.setText("hello");
		save(g1);
		
		Greeting g2 = new Greeting();
		g2.setText("hola");
		save(g2);
		
	}
	
	@RequestMapping(value="/greetings", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Greeting>> getGreetings(){
		
		Collection<Greeting> greetings = greetingMap.values();
		
		return new ResponseEntity<Collection<Greeting>> (greetings, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/greetings/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> getGreeting(@PathVariable("id") BigInteger id) {
		Greeting greeting = greetingMap.get(id);
		
		if(greeting == null) {
			return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Greeting> (greeting, HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value = "/greetings", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting) {
		
		Greeting savedGreeting = save(greeting);
		return new ResponseEntity<Greeting> (savedGreeting, HttpStatus.CREATED);
	}
	
	public static boolean delete(BigInteger id) {
		Greeting deleteGreeting = greetingMap.remove(id);
		if(deleteGreeting == null) {
			return false;
		}
		return true;
	}
	
	@RequestMapping(
			value = "/greetings/{id}", 
			method = RequestMethod.DELETE, 
			consumes = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<Greeting> deleteGreeting(@PathVariable("id") BigInteger id, @RequestBody Greeting greeting) {
		boolean deleted = delete(id);
		if(!deleted) {
			return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
	}
	
}
