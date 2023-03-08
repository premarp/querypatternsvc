package com.skilltracker.querypatternsvc.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillProfileResonseBean {
	
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

}
