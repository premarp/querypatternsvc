package com.skilltracker.querypatternsvc.bean;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillProfileRequestBean {
	
	@NotBlank(message = "Name is mandatory")
	@Size(min = 5, max = 30)
	private String name;
	
	@NotBlank(message = "associateId is mandatory")
	@Size(min = 5, max = 30)
	private String associateId;
	
	@NotBlank(message = "mobile is mandatory")
	@Size(min = 10, max = 10)
	@Positive
	private String mobile;
	
	@NotBlank(message = "email is mandatory")
	@Email(message="Please provide a valid email address")
	@Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
	private String email;
	
	@Valid
	private List<SkillSet> technicalSkills;
	@Valid
	private List<SkillSet> nonTechnicalSkills;
	
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<SkillSet> getTechnicalSkills() {
		return technicalSkills;
	}
	public void setTechnicalSkills(List<SkillSet> technicalSkills) {
		this.technicalSkills = technicalSkills;
	}
	public List<SkillSet> getNonTechnicalSkills() {
		return nonTechnicalSkills;
	}
	public void setNonTechnicalSkills(List<SkillSet> nonTechnicalSkills) {
		this.nonTechnicalSkills = nonTechnicalSkills;
	}
	@Override
	public String toString() {
		return "SkillProfileRequestBean [name=" + name + ", associateId=" + associateId + ", mobile=" + mobile
				+ ", email=" + email + ", technicalSkills=" + technicalSkills + ", nonTechnicalSkills="
				+ nonTechnicalSkills + "]";
	}

	

}
