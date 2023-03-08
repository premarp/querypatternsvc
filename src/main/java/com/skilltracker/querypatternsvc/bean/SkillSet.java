package com.skilltracker.querypatternsvc.bean;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class SkillSet {
	
	@NotBlank(message = "skillName is mandatory")
	private String skillName;
	
	@NotNull(message = "expertiseLevel is mandatory")
	@Range(min=0,max=20)
	private int expertiseLevel;
	
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	public int getExpertiseLevel() {
		return expertiseLevel;
	}
	public void setExpertiseLevel(int expertiseLevel) {
		this.expertiseLevel = expertiseLevel;
	}
	
	

}
