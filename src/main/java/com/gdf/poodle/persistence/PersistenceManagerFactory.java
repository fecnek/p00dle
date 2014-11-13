package com.gdf.poodle.persistence;

import java.io.File;
import java.io.FileNotFoundException;

public interface PersistenceManagerFactory {
	public PersistenceManager createPersistenceContext(File persistenceContextFile) throws FileNotFoundException;
	public PersistenceManager createPersistenceContext(String persistenceContextFileName) throws FileNotFoundException;
}