package com.gdf.poodle.persistence;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PersistenceDescriptor implements Serializable {

	private static final long serialVersionUID = 1L;
	
	Long actualSequenceNumber;
	Map<Long, Object> entities;
	
	public PersistenceDescriptor() {
		entities = new HashMap<Long, Object>();
		actualSequenceNumber = 0L;
	}
	
	
	public synchronized Long getActualSequenceNumber() {
		return actualSequenceNumber;
	}
	
	public synchronized void setActualSequenceNumber(Long actualSequenceNumber) {
		this.actualSequenceNumber = actualSequenceNumber;
	}
	
	public Map<Long, Object> getEntities() {
		return entities;
	}
	
	public void setEntities(Map<Long, Object> entities) {
		this.entities = entities;
	}


	public synchronized Long getNextSequenceNumber() {
		this.actualSequenceNumber = this.actualSequenceNumber + 1;
		return this.actualSequenceNumber;
	}
	
}
