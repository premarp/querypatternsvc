package com.skilltracker.querypatternsvc.bean;

import lombok.Data;

@Data
public class SkillProfileSearchBean {

	private String name;
	private String associateId;
	private String skillName;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAssociateId() {
		return associateId;
	}
	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	
	
}
