package com.gdf.poodle.controllers.forms;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.gdf.poodle.repositories.entities.Survey;

public @Data class CampaignData {
		
		private Long id;
		
		private String name;
		private Date   periodStart;
		private Date   periodEnd;
		
		private List<Survey> surveys;
}
