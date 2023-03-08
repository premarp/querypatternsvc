package com.skilltracker.querypatternsvc.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.skilltracker.querypatternsvc.bean.SkillTrackerResponseBean;
import com.skilltracker.querypatternsvc.bean.repo.SkillTracker;
import com.skilltracker.querypatternsvc.config.RedisRepository;
import com.skilltracker.querypatternsvc.manager.SkillProfileManager;
import org.springframework.kafka.annotation.KafkaListener;

import com.skilltracker.querypatternsvc.bean.SkillSet;
import com.common.dto.SkillSetCommon;
import com.common.dto.SkillTrackerCommon;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class SkillProfileController {
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	SkillProfileManager manager;

	private static final Logger logger = LogManager.getLogger(SkillProfileController.class);

	@GetMapping("welcome")
	public String searchSkillProfile() {
		return "Welcome to azure";
	}

	@Autowired
	RedisRepository redisRepository;

	@Autowired
	@Qualifier("skillTracker")
	RedisTemplate<String, Object> template;
	
	 @KafkaListener(groupId = "SKILLTRACKER",topics = "SKILLTRACKER_ADD", containerFactory = "kafkaListenerContainerFactory")
	    public void consumeSkillProfile(SkillTrackerCommon skillProfile) {
			SkillTracker record = new SkillTracker();
			
			record.setAssociateId(skillProfile.getAssociateId());
			record.setEmail(skillProfile.getEmail());
			record.setMobile(skillProfile.getMobile());
			record.setName(skillProfile.getName());
			List<SkillSet> techSkill = new ArrayList();
			skillProfile.getTechnicalSkills().forEach(skillSet -> {
				SkillSet skill = new SkillSet();
				skill.setSkillName(skillSet.getSkillName());
				skill.setExpertiseLevel(skillSet.getExpertiseLevel());
				techSkill.add(skill);
			});
			
			List<SkillSet> nonTechSkill = new ArrayList();
			skillProfile.getNonTechnicalSkills().forEach(skillSet -> {
				SkillSet skill = new SkillSet();
				skill.setSkillName(skillSet.getSkillName());
				skill.setExpertiseLevel(skillSet.getExpertiseLevel());
				nonTechSkill.add(skill);
			});
			
			record.setTechnicalSkills(techSkill);
			record.setNonTechnicalSkills(nonTechSkill);
			record.setCreateDate(LocalDate.now());
			record.setUpdateDate(LocalDate.now());
			String uniqueID = UUID.randomUUID().toString();
			record.setUserId(uniqueID);
			Query query = new Query();
			query.addCriteria(Criteria.where("associateId").is(record.getAssociateId()));
			SkillTracker obj = mongoTemplate.findOne(query, SkillTracker.class);
			if(obj == null) {
				record = mongoTemplate.insert(record);
			}else {

				Update update = new Update();
				update.set("technicalSkills",record.getTechnicalSkills());
				update.set("nonTechnicalSkills",record.getNonTechnicalSkills());
				update.set("updateDate", LocalDate.now());
				mongoTemplate.upsert(query, update, SkillTracker.class);
			}
			
			logger.info("Record Inserted succesfully for associateId :{} OjectId:{}", skillProfile.getAssociateId(),
					record.getId());
	    }


	@CrossOrigin(origins = "https://skillprofileadmin.azurewebsites.net")
	@GetMapping(value = "/admin/{criteria}/{criteraValue}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public SkillTrackerResponseBean searchSkillProfile(HttpServletRequest request,
			@PathVariable("criteria") String criteria, @PathVariable("criteraValue") String criteraValue)
			throws Exception {
		String redisKey = criteria + "_" + criteraValue;
		SkillTrackerResponseBean bean = new SkillTrackerResponseBean();
		if (null != redisRepository.getValue(criteria + "_" + criteraValue)) {
			logger.info("Key already available in redis : {}", redisKey);
			bean = (SkillTrackerResponseBean) redisRepository.getValue(criteria + "_" + criteraValue);
		} else {
			logger.info("Key not available in redis : {}", redisKey);
			List<SkillTracker> data = manager.getSearchCritera(criteria, criteraValue);
			bean.setResult(data);
			redisRepository.setValue(criteria + "_" + criteraValue, bean);
		}

		return bean;

	}

	

//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity handle(MethodArgumentNotValidException methodArgumentNotValidException) {
//	    Set<ConstraintViolation<?>> violations = methodArgumentNotValidException.getBindingResult()
//	    String errorMessage = "";
//	    if (!violations.isEmpty()) {
//	        StringBuilder builder = new StringBuilder();
//	        violations.forEach(violation -> builder.append(" " + violation.getMessage()));
//	        errorMessage = builder.toString();
//	    } else {
//	        errorMessage = "ConstraintViolationException occured.";
//	    }
//	    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
//	 }

}
