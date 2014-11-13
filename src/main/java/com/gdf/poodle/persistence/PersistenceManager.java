package com.gdf.poodle.persistence;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.gdf.poodle.persistence.exceptions.IdFieldNotFoundException;
import com.gdf.poodle.persistence.exceptions.NoResultException;
import com.gdf.poodle.persistence.exceptions.PersistFailedException;

public interface PersistenceManager {

	/**
	 * It stores the whole persistenceContext's content into the xml file which has been initialized
	 * during instance creation
	 * 
	 * @throws FileNotFoundException
	 */
	public void flush() throws FileNotFoundException;

	/**
	 * It loads the content of the xml file into the persistenceContext which has been initialized 
	 * during instance creation
	 * 
	 * @throws FileNotFoundException
	 */
	public void refresh() throws FileNotFoundException;

	/**
	 * It attaches the entity parameter to the persistence context. If there is missing Id then it 
	 * will assign a new generated id to the id field which has to be a Long typed
	 * 
	 * @param entity
	 * @throws IdFieldNotFoundException 
	 * @throws NoResultException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public <T> void merge(T entity) throws IdFieldNotFoundException,
			IllegalArgumentException, IllegalAccessException, NoResultException;

	/**
	 * It attaches the new entity to the persistence context. The missing Id will be 
	 * filled with a new generated id. If the entity contains an Id which is not null then
	 * an PersistFailedException will be thrown.
	 * 
	 * @return entity filled with new Id
	 * @param entity
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws PersistFailedException 
	 */
	public <T> T persist(T entity) throws IdFieldNotFoundException,
			IllegalArgumentException, IllegalAccessException, NoResultException, PersistFailedException;
	
	/**
	 * It finds the given entityClass type in the persistenceContext. 
	 * If it exists then it will find the corresponding entity instance also and returns with it if
	 * it exists as well
	 * 
	 * @param entityClass
	 * @param id
	 * @return <T>
	 * @throws NoResultException
	 */
	public <T> T find(Class<T> entityClass, Long id)
			throws NoResultException;

	
	
	/**
	 * It returns with the xmlPath which has been initialized during the startup 
	 * 
	 * @return String
	 */
	public String getXmlPath();

	/**
	 * It removes the given entity from the persistence context.
	 * 
	 * @param entity
	 * @throws IdFieldNotFoundException 
	 * @throws NoResultException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public <T> void remove(T entity) throws IllegalArgumentException, IllegalAccessException, NoResultException, IdFieldNotFoundException;
	
	public <T> List<T> select(Class<T> entityClass, Predicate<T> predicate);
	
	public <T> Optional<T> selectSingle(Class<T> entityClass, Predicate<T> predicate);

}