package com.skilltracker.querypatternsvc.bean.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.skilltracker.querypatternsvc.bean.SkillSet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="skilltracker")
public class SkillTracker {
	
	@Id	
	private String id;
	private String name;
	private String associateId;
	private String email;
	private String mobile;
	private List<SkillSet> technicalSkills;
	private List<SkillSet> nonTechnicalSkills;
	private String userId;
	//@JsonSerialize(using = LocalDateSerializer.class)
	//@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate createDate;
	//@JsonSerialize(using = LocalDateSerializer.class)
	//@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate updateDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public LocalDate getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}
	public LocalDate getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
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

	
}
