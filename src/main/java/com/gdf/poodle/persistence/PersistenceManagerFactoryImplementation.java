package com.gdf.poodle.persistence;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.stereotype.Component;

@Component("persistenceManagerFactory")
public class PersistenceManagerFactoryImplementation implements PersistenceManagerFactory {

	public PersistenceManager createPersistenceContext(File persistenceContextFile) throws FileNotFoundException {
		return new PersistenceManagerImplementation(persistenceContextFile.getAbsolutePath());
	}

	public PersistenceManager createPersistenceContext(String persistenceContextFileName) throws FileNotFoundException {
		return createPersistenceContext(new File(persistenceContextFileName));
	}
}
