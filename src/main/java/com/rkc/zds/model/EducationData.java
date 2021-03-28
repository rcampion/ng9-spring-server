package com.rkc.zds.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EducationData {
	
	@JsonProperty("EDUCATION")	
	private String EDUCATION;
	
	public String getEDUCATION() {
		return EDUCATION;
	}

	public void setEDUCATION(String ed) {
		EDUCATION = ed;
	}

	@JsonFormat(shape=JsonFormat.Shape.ARRAY)
	private List<String> education_and_training;
	
	public List<String> getEducation_and_training() {
		return education_and_training;
	}

	public void setEducation_and_training(List<String> education_and_training) {
		this.education_and_training = education_and_training;
	}
}
