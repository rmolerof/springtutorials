package com.training.service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.training.model.Greeting;

@Service
@Transactional(
		propagation=Propagation.SUPPORTS,
		readOnly = true)
public class GreetingServiceImpl implements GreetingService {
	
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
	
	public static boolean delete(BigInteger id) {
		Greeting deleteGreeting = greetingMap.remove(id);
		if(deleteGreeting == null) {
			return false;
		}
		return true;
	}
	
	public Collection<Greeting> findAll() {
		return greetingMap.values();
	}

	public Greeting findOne(Long id) {
		return greetingMap.get(BigInteger.valueOf(id));
	}

	@Transactional(
			propagation=Propagation.REQUIRED,
			readOnly = false)
	public Greeting create(Greeting greeting) {
		Greeting savedGreeting = save(greeting); 
		
		if(savedGreeting.getId().longValue() == 4L){
			throw new RuntimeException("Roll me back");
		}
		
		return savedGreeting;
	}
	
	@Transactional(
			propagation=Propagation.REQUIRED,
			readOnly = false)
	public Greeting update(Greeting greeting) {
		return greetingMap.put(greeting.getId(), greeting);
	}
	
	@Transactional(
			propagation=Propagation.REQUIRED,
			readOnly = false)
	public boolean delete(long id) {
		return delete(BigInteger.valueOf(id));
	}

}
