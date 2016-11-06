package com.mcbain.sitewatcher.repository;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

//import com.mcbain.sitewatcher.Application;
//import com.mcbain.sitewatcher.ApplicationConfig;
import com.mcbain.sitewatcher.domain.Probe;

@RunWith(SpringRunner.class)
//@ContextConfiguration(classes={ApplicationConfig.class})
@DataJpaTest
public class ProbeRepoTest {
	
	@Autowired
	private ProbeRepository repo;
	
	private Probe probe;
	
	@Before
	@Transactional
	@Rollback(false)
	public void setUp() {
		probe = new Probe("first site","www.my.com");
		probe = repo.save(probe);
	}
	
	@Test
	@Transactional
	public void testRepoSetUpWithOneItem() {
		assertNotNull(repo.findOne(probe.getId()));
	}
	
	@Test
	@Transactional
	public void testRepoSetUpWithCorrectItem() {
		assertEquals(repo.findOne(probe.getId()),probe);
	}
	
	@Test
	@Transactional
	public void testSavedFromUpdate() {
		Probe updatedProbe = new Probe(probe.getId(),"first site","web.my.com");
		Probe tempProbe = repo.save(updatedProbe);
		assertEquals(tempProbe,updatedProbe);
	}
	
	@Test
	@Transactional
	public void testSavedAnotherYieldsTwo() {
		Probe updatedProbe = new Probe("second site","second.my.com");
		Probe tempProbe = repo.save(updatedProbe);
		assertEquals(tempProbe,updatedProbe);
	}
	
	@Test
	@Transactional
	public void testDeletedItemNotFound() {
		repo.delete(probe.getId());
		assertNull(repo.findOne(probe.getId()));
	}
	
}
