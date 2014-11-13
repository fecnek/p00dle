package com.gdf.poodle.persistence;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;

import com.gdf.poodle.persistence.PersistenceManager;
import com.gdf.poodle.persistence.entity.SimpleOrder;
import com.gdf.poodle.persistence.exceptions.IdFieldNotFoundException;
import com.gdf.poodle.persistence.exceptions.NoResultException;
import com.gdf.poodle.persistence.exceptions.PersistFailedException;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="**/*-context.xml")
@WebAppConfiguration

@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
@ComponentScan
@Ignore
public class PersistenceManagerTestCase {

	private static final String PERSISTENCE_CONTEXT_FILE_NAME = "persistenceContext.xml";
	@Autowired
	@Qualifier(value="persistenceManagerFactory")
	PersistenceManagerFactory persistenceManagerFactory;
	
	
	@Rule
    public TemporaryFolder folder= new TemporaryFolder();
	
	@Test
	public void testCreatePersistenceContext() {
		try {
			File persistenceContextFile = folder.newFile(PERSISTENCE_CONTEXT_FILE_NAME);

			PersistenceManager manager = persistenceManagerFactory.createPersistenceContext(persistenceContextFile);
			assertNotNull(manager);
			assertNotNull(manager.getXmlPath());
			assertEquals(persistenceContextFile.getAbsolutePath(), manager.getXmlPath());
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAttachEntityToPersistenceContext() {
		SimpleOrder order = new SimpleOrder();
		order.setCreationDate(new Date());
		order.setOrderName("TEST_ORDER");
		order.setOrderNumber("012333");
		try {
			PersistenceManager manager = persistenceManagerFactory.createPersistenceContext(PERSISTENCE_CONTEXT_FILE_NAME);
			manager.merge(order);		
		} catch (IOException | IdFieldNotFoundException | IllegalArgumentException | IllegalAccessException | NoResultException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testAttachEntityToPersistenceContextNull() {
		
		try {
			PersistenceManager manager = persistenceManagerFactory.createPersistenceContext(PERSISTENCE_CONTEXT_FILE_NAME);
			manager.merge(null);
			fail();
		} catch (NullPointerException | IOException | IdFieldNotFoundException | IllegalArgumentException | IllegalAccessException | NoResultException e) {
			
		}
	}
	
	
	@Test
	public void testStoreAndLoadPersistenceContext() {
		SimpleOrder order = new SimpleOrder();
		order.setCreationDate(new Date());
		order.setOrderName("TEST_ORDER");
		order.setOrderNumber("012333");
		try {
			PersistenceManager manager = persistenceManagerFactory.createPersistenceContext(PERSISTENCE_CONTEXT_FILE_NAME);
			order = manager.persist(order);		
			manager.flush();
			
			
			PersistenceManager newManager = persistenceManagerFactory.createPersistenceContext(PERSISTENCE_CONTEXT_FILE_NAME);
			SimpleOrder newOrder = newManager.find(SimpleOrder.class, order.getId());
			
			assertThat(newOrder, is(notNullValue()));
			assertThat(newOrder.getId(), is (order.getId()));
			
		} catch (IOException | IdFieldNotFoundException | IllegalArgumentException | IllegalAccessException | NoResultException | PersistFailedException e) {
			
		}
	}

	
	@Test
	public void testPersistEntityWithoutNullInId() {
		SimpleOrder order = new SimpleOrder();
		order.setId(10L);
		order.setCreationDate(new Date());
		order.setOrderName("TEST_ORDER");
		order.setOrderNumber("012333");
		try {
			File persistenceContextFile = folder.newFile(PERSISTENCE_CONTEXT_FILE_NAME);
			PersistenceManager manager = persistenceManagerFactory.createPersistenceContext(persistenceContextFile);
			manager.persist(order);		
			fail();
		} catch (IOException | IdFieldNotFoundException | IllegalArgumentException | IllegalAccessException | NoResultException | PersistFailedException e) {
			
		}
	}
	
	@Test
	public void testPersistEntityWithNullInId() {
		SimpleOrder order = new SimpleOrder();
		order.setCreationDate(new Date());
		order.setOrderName("TEST_ORDER");
		order.setOrderNumber("012333");
		try {
			File persistenceContextFile = folder.newFile(PERSISTENCE_CONTEXT_FILE_NAME);
			PersistenceManager manager = persistenceManagerFactory.createPersistenceContext(persistenceContextFile);
			order = manager.persist(order);		
			assertNotNull(order.getId());
		} catch (IOException | IdFieldNotFoundException | IllegalArgumentException | IllegalAccessException | NoResultException | PersistFailedException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testFindEntity() {
		SimpleOrder order = new SimpleOrder();
		order.setCreationDate(new Date());
		order.setOrderName("TEST_ORDER");
		order.setOrderNumber("012333");
		try {
			File persistenceContextFile = folder.newFile(PERSISTENCE_CONTEXT_FILE_NAME);
			PersistenceManager manager = persistenceManagerFactory.createPersistenceContext(persistenceContextFile);
			order = manager.persist(order);
			
			Long id = order.getId();
			
			SimpleOrder newOrder = manager.find(SimpleOrder.class, id);
			assertThat(newOrder, is(notNullValue()));
			assertThat(newOrder, is(order));
		} catch (IOException | IdFieldNotFoundException | IllegalArgumentException | IllegalAccessException | NoResultException | PersistFailedException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testRemoveEntity() {
		SimpleOrder order = new SimpleOrder();
		order.setCreationDate(new Date());
		order.setOrderName("TEST_ORDER");
		order.setOrderNumber("012333");
		try {
			File persistenceContextFile = folder.newFile(PERSISTENCE_CONTEXT_FILE_NAME);
			PersistenceManager manager = persistenceManagerFactory.createPersistenceContext(persistenceContextFile);
			order = manager.persist(order);
			Long id = order.getId();
			
			SimpleOrder newOrder = manager.find(SimpleOrder.class, id);
			assertThat(newOrder, is(notNullValue()));
			
		    manager.remove(order);
		    
		    try {
		    	manager.find(SimpleOrder.class, id);
		    	fail();
		    } 
		    catch (NoResultException exception) {
		    	
		    }

		} catch (IOException | IdFieldNotFoundException | IllegalArgumentException | IllegalAccessException | NoResultException | PersistFailedException e) {
		}
	}
}
