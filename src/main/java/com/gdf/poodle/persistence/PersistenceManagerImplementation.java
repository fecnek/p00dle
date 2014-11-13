package com.gdf.poodle.persistence;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.lang.reflect.*;

import com.gdf.poodle.persistence.annotations.Entity;
import com.gdf.poodle.persistence.annotations.PrimaryKey;
import com.gdf.poodle.persistence.exceptions.IdFieldNotFoundException;
import com.gdf.poodle.persistence.exceptions.NoResultException;
import com.gdf.poodle.persistence.exceptions.PersistFailedException;

@SuppressWarnings("rawtypes")
public class PersistenceManagerImplementation implements PersistenceManager  {
    String xmlFilePath;
	Map<Class, PersistenceDescriptor> persistenceContext;
	
	PersistenceManagerImplementation(String xmlFilePath) throws FileNotFoundException {
		this.xmlFilePath = xmlFilePath;
		persistenceContext = new HashMap<Class, PersistenceDescriptor>();
		File xmlFile = new File(xmlFilePath);
		if (xmlFile.exists())
		{
			refresh();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.gdf.poodle.persistence.PersistenceManager#flush()
	 */
	@Override
	public void flush() throws FileNotFoundException {
		XMLEncoder xmlEncoder = new XMLEncoder(
									new BufferedOutputStream(
											new FileOutputStream(xmlFilePath)
									)
								);
		xmlEncoder.writeObject(persistenceContext);
		xmlEncoder.close();
	}
	
	/* (non-Javadoc)
	 * @see org.gdf.poodle.persistence.PersistenceManager#refresh()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void refresh() throws FileNotFoundException {
		XMLDecoder xmlDecoder = new XMLDecoder(
                					new BufferedInputStream(
                							new FileInputStream(xmlFilePath)
                					)
                				);
		try {
			persistenceContext = (Map<Class, PersistenceDescriptor>) xmlDecoder.readObject();
		}
		catch (ArrayIndexOutOfBoundsException exception) {
			
		}
		finally {
			xmlDecoder.close();
		}
	}

	
	/* (non-Javadoc)
	 * @see org.gdf.poodle.persistence.PersistenceManager#merge(T entity)
	 */
	@Override
	public <T> void merge(T entity) throws IdFieldNotFoundException, IllegalArgumentException, IllegalAccessException, NoResultException {
		if (entity == null) {
			throw new NullPointerException(PersistenceMessages.ENTITY_IS_NULL.toString());
		}
		
		Class<? extends Object> entityClass = entity.getClass();
		if (!entityClass.isAnnotationPresent(Entity.class)) {
			throw new IllegalArgumentException();
		}
		
		Long entityId = getEntityId(entity);
		if (entityId == null) {
			entityId = getNextSequenceNumber(entityClass);
			setNewEntityId(entity, entityId);
		}
		else {
			find(entityClass, entityId);
		}
			
		PersistenceDescriptor descriptor = getPersistenceDescriptor(entityClass);
		Map<Long, Object> entities = descriptor.getEntities();
		entities.put(entityId, entity);
	}

	private void setNewEntityId(Object entity, Long entityId) throws IllegalArgumentException, IllegalAccessException, IdFieldNotFoundException {
		Class<? extends Object> entityClass = entity.getClass();
		Field[] fields = entityClass.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);

			if (field.isAnnotationPresent(PrimaryKey.class)) {
			  field.set(entity, entityId);
			  return;
			}
		}
		
		throw new IdFieldNotFoundException();
	}

	private Long getNextSequenceNumber(Class<? extends Object> entityClass) {
		PersistenceDescriptor descriptor = getPersistenceDescriptor(entityClass);
		return descriptor.getNextSequenceNumber();
	}

	private PersistenceDescriptor getPersistenceDescriptor(
			Class<? extends Object> entityClass) {
		PersistenceDescriptor descriptor;
		if (!persistenceContext.containsKey(entityClass)) {
			descriptor = new PersistenceDescriptor();
			persistenceContext.put(entityClass, descriptor);
		}
		else {
			descriptor = persistenceContext.get(entityClass);
		}
		return descriptor;
	}

	private Long getEntityId(Object entity) throws IdFieldNotFoundException, IllegalArgumentException, IllegalAccessException {
		Class<? extends Object> entityClass = entity.getClass();
		Field[] fields = entityClass.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(PrimaryKey.class)) {
				return (Long) field.get(entity);
			}
		}
		
		throw new IdFieldNotFoundException();
	}

	/* (non-Javadoc)
	 * @see org.gdf.poodle.persistence.PersistenceManager#find(java.lang.Class, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public<T> T find(Class<T> entityClass, Long id) throws NoResultException {
		if (!persistenceContext.containsKey(entityClass)) {
			throw new NoResultException();
		}
		
		PersistenceDescriptor descriptor = getPersistenceDescriptor(entityClass);
		Map<Long, Object> entities = descriptor.getEntities();
		
		if (entities.containsKey(id) ) {
			return (T) entities.get(id);
		}
		else {
			throw new NoResultException();
		}
	}

	/* (non-Javadoc)
	 * @see org.gdf.poodle.persistence.PersistenceManager#getXmlPath()
	 */
	@Override
	public String getXmlPath() {
		return xmlFilePath;
	}

	/* (non-Javadoc)
	 * @see org.gdf.poodle.persistence.PersistenceManager#persist(T entity)
	 */
	@Override
	public <T> T persist(T entity) throws IdFieldNotFoundException,
			IllegalArgumentException, IllegalAccessException, NoResultException, PersistFailedException {
		if (entity == null) {
			throw new NullPointerException(PersistenceMessages.ENTITY_IS_NULL.toString());
		}
		
		Class<? extends Object> entityClass = entity.getClass();
		if (!entityClass.isAnnotationPresent(Entity.class)) {
			throw new IllegalArgumentException();
		}
		
		Long entityId = getEntityId(entity);
		if (entityId == null) {
			entityId = getNextSequenceNumber(entityClass);
			setNewEntityId(entity, entityId);
		}
		else {
			throw new PersistFailedException();
		}
		
		PersistenceDescriptor descriptor = getPersistenceDescriptor(entityClass);
		Map<Long, Object> entities = descriptor.getEntities();
		entities.put(entityId, entity);
		
		return entity;
	}

	/* (non-Javadoc)
	 * @see org.gdf.poodle.persistence.PersistenceManager#remove(T entity)
	 */
	@Override
	public <T> void remove(T entity) throws IllegalArgumentException, IllegalAccessException, IdFieldNotFoundException, NoResultException {
		if (entity == null) {
			throw new NullPointerException(PersistenceMessages.ENTITY_IS_NULL.toString());
		}
		
		Class<? extends Object> entityClass = entity.getClass();
		
		Long id = getEntityId(entity);		

		PersistenceDescriptor descriptor = getPersistenceDescriptor(entityClass);
		Map<Long, Object> entities = descriptor.getEntities();
		if (entities.containsKey(id) ) {
		    entities.remove(id);
		}
		else {
			throw new NoResultException();
		}
	}

	@Override
	public <T> List<T> select(Class<T> entityClass, Predicate<T> predicate) {
		PersistenceDescriptor descriptor = getPersistenceDescriptor(entityClass);
		@SuppressWarnings("unchecked")
		Map<Long, T> entities = (Map<Long, T>) descriptor.getEntities();
		
		List<T> resultList = new ArrayList<>();
		for(T entity : entities.values()) {
			if (predicate.test(entity)) {
				resultList.add(entity);
			}
		}
		
		return resultList;
	}
	
	@Override
	public <T> Optional<T> selectSingle(Class<T> entityClass, Predicate<T> predicate) {
		PersistenceDescriptor descriptor = getPersistenceDescriptor(entityClass);
		@SuppressWarnings("unchecked")
		Map<Long, T> entities = (Map<Long, T>) descriptor.getEntities();
		
		for(T entity : entities.values()) {
			if (predicate.test(entity)) {
				return Optional.of(entity);
			}
		}
		
		return Optional.empty();
	}
}
