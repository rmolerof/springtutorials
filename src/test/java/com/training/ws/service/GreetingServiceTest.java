package com.training.ws.service;

import java.math.BigInteger;
import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.training.model.Greeting;
import com.training.service.GreetingService;
import com.training.ws.AbstractTest;

public class GreetingServiceTest extends AbstractTest{
	
	@Autowired
	private GreetingService service;
	
	@Before
	public void setUp(){
		service.evictCache();
	}
	
	@After
	public void tearDown() {
		
	}
	
	@Test
	public void testFindAll(){
		LOG.info("--testFindAll");
		Collection<Greeting> list = service.findAll();
		Assert.assertNotNull("failusre - expected result", list);
		Assert.assertEquals("failure - expected size", 2, list.size());
	}
	
	@Test
	public void testFindOne(){
		Long id = new Long (1);
		Greeting entity = service.findOne(id);
		Assert.assertNotNull("failure - expected not null", entity);
		Assert.assertEquals("failure - expected id attribute match", BigInteger.valueOf(id), entity.getId());
	}
	
	@Test
	public void testFindOneNotFound(){
		Long id = Long.MAX_VALUE;
		Greeting entity = service.findOne(id);
		Assert.assertNull("failure - expected null", entity);
	}
	
	@Test
	public void testCreate() {
		Greeting entity = new Greeting();
		entity.setText("test");
		
		Greeting createdEntity = service.create(entity);
		
		Assert.assertNotNull("failure - expected not null", entity);
		Assert.assertNotNull("failure - expected id attribute not null", createdEntity.getId());
		Assert.assertEquals("failure - expected text attribute match", "test", createdEntity.getText());
		Collection<Greeting> list = service.findAll();
		Assert.assertEquals("failure - expected size", 3, list.size());
		
	}
	
	/*@Test
	public void testCreateWithId() {
		Exception e = null;
		Greeting entity = new Greeting();
		entity.setId(BigInteger.valueOf(Long.MAX_VALUE));
		entity.setText("test");
		
		try {
			service.create(entity);
		} catch(Exception ex) {
			e = ex;
			LOG.error("failed to create entity", ex);
		}
		
		Assert.assertNotNull("failure - expected exception", e);
	}*/
	
	@Test
	public void testUpdate() {
		
		Long id = new Long(1);
		Greeting entity = service.findOne(id);
		
		Assert.assertNotNull("failure - expected not null", entity);
		String updatedText = entity.getText() + " test";
		entity.setText(updatedText);
		
		Greeting updatedEntity = service.update(entity);
		
		Assert.assertNotNull("failure - expected not null", updatedEntity);
		Assert.assertEquals("failure - expected updated entity not null", BigInteger.valueOf(id), updatedEntity.getId());
		Assert.assertEquals("failure - expected updated entity ext attribute match", updatedText, updatedEntity.getText());
	}
	
	@Test
	public void testDelete() {
		Long id = new Long (2);
		Greeting entity = service.findOne(id);
		Assert.assertNotNull("failure - expected not null", entity);
		
		service.delete(id);
		
		Collection<Greeting> list = service.findAll();
		Assert.assertEquals("failure - expected size",  2, list.size());
		Greeting deletedEntity = service.findOne(id);
		Assert.assertNull("failure - expected entity to be deleted", deletedEntity);
	}
}