package com.skilltracker.querypatternsvc.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.skilltracker.querypatternsvc.bean.repo.SkillTracker;
import com.skilltracker.querypatternsvc.controller.SkillProfileController;

@Configuration
public class SkillProfileManager {

	@Autowired
	MongoTemplate mongoTemplate;

	private static final Logger logger = LogManager.getLogger(SkillProfileController.class);

	public List<SkillTracker> getSearchCritera(String criteria, String criteraValue) throws Exception {
		Query query = new Query();
		if ("name".equalsIgnoreCase(criteria)) {
			query.addCriteria(Criteria.where(criteria).regex("^" + criteraValue));
		} else if ("associateId".equalsIgnoreCase(criteria)) {
			query.addCriteria(Criteria.where(criteria).is(criteraValue));
		} else if ("skillName".equalsIgnoreCase(criteria)) {
			query.addCriteria(Criteria.where("technicalSkills")
					.elemMatch(Criteria.where(criteria).is(criteraValue).and("expertiseLevel").gt(10)));
			List<SkillTracker> technicalSkillsData = mongoTemplate.find(query, SkillTracker.class);
			query = new Query();
			query.addCriteria(Criteria.where("nonTechnicalSkills")
					.elemMatch(Criteria.where(criteria).is(criteraValue).and("expertiseLevel").gt(10)));
			List<SkillTracker> nonTechnicalSkillsData = mongoTemplate.find(query, SkillTracker.class);
			return getConsolidatedData(technicalSkillsData, nonTechnicalSkillsData);

		} else {
			throw new Exception("Unsupported search Critera");
		}

		List<SkillTracker> users = mongoTemplate.find(query, SkillTracker.class);
		return users;

	}

	private List<SkillTracker> getConsolidatedData(List<SkillTracker> technicalSkillsData,
			List<SkillTracker> nonTechnicalSkillsData) {
		List<SkillTracker> finalList = new ArrayList<>();
		finalList.addAll(technicalSkillsData);
		finalList.addAll(nonTechnicalSkillsData);
		return finalList;
	}

}
