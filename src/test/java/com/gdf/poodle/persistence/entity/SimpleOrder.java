package com.gdf.poodle.persistence.entity;

import com.gdf.poodle.persistence.annotations.Entity;
import com.gdf.poodle.persistence.annotations.PrimaryKey;

import java.util.Date;

import lombok.Data;

@Entity
public @Data class SimpleOrder {
	
	@PrimaryKey
	private Long id;
	private String orderName;
	private String orderNumber;
	private Date creationDate;

	
}
