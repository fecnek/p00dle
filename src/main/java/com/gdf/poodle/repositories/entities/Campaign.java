package com.gdf.poodle.repositories.entities;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.gdf.poodle.persistence.annotations.Entity;
import com.gdf.poodle.persistence.annotations.PrimaryKey;

@Entity
public @Data class Campaign {
	
	@PrimaryKey
	private Long id;
	
	private String name;
	private Date   periodStart;
	private Date   periodEnd;
	
	private List<Survey> surveys;
	private SystemUser owner;
}
