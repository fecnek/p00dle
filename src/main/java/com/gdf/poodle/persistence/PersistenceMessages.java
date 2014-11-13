package com.gdf.poodle.persistence;

public enum PersistenceMessages {
	ENTITY_IS_NULL("Parameter entity is null");

	private String value;
	
	PersistenceMessages(String initValue) {
		value = initValue;
	}

	@Override
	public String toString() {
		return value;
	}
	
}
