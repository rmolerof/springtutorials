package com.training.service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.training.model.Greeting;

@Service
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
	
	@Override
	public Collection<Greeting> findAll() {
		return greetingMap.values();
	}

	@Override
	public Greeting findOne(Long id) {
		return greetingMap.get(BigInteger.valueOf(id));
	}

	@Override
	public Greeting create(Greeting greeting) {
		return save(greeting);
	}

	@Override
	public Greeting update(Greeting greeting) {
		return greetingMap.put(greeting.getId(), greeting);
	}

	@Override
	public boolean delete(long id) {
		return delete(BigInteger.valueOf(id));
	}

}
