package com.skilltracker.querypatternsvc.bean;

import java.util.List;

import com.skilltracker.querypatternsvc.bean.repo.SkillTracker;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillTrackerResponseBean {
	
	List<SkillTracker> result ;

	public void setResult(List<SkillTracker> data) {
		result = data;
	}
	

}
