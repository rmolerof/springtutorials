package com.training.service;

import java.util.Collection;

import com.training.model.Greeting;

public interface GreetingService {
	public Collection<Greeting> findAll();
	public Greeting findOne(Long id);
	public Greeting create(Greeting greeting);
	public Greeting update(Greeting greeting);
	public boolean delete(long id);
}
