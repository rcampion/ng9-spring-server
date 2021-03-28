package com.rkc.zds.entity;

import java.io.Serializable;
import java.sql.Clob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rkc.zds.model.ResumeData;

/**
 * The persistent class for the PCM_CONTACT_EMAILS database table.
 * 
 */
@Entity
@Table(name = "PCM_USER_RESUME")
public class ResumeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer resumeId;

	@Column(name = "USER_ID")
	private int userId;
	
	@Column(name="FIRSTNAME")	
	private String firstName;

	@Column(name="LASTNAME")	
	private String lastName;

	@Column(name="ORIGINAL_FILE_NAME")	
	private String originalFileName;

	@Column(name="PDF_FILE_NAME")	
	private String pdfFileName;
	
	@Column(name="HTML_FILE_NAME")	
	private String htmlFileName;
	
	@Column(name = "JSON_RESUME")
    @Lob
    @Type(type = "org.hibernate.type.TextType")
	private String jsonResume;

	@Transient
	private ResumeData resumeData;
	
	@Transient
	private String htmlResume;
	
	public ResumeData getResumeData() {
		return resumeData;
	}

	public void setResumeData(ResumeData parsed) {
		this.resumeData = parsed;
	}

	public Integer getResumeId() {
		return resumeId;
	}

	public void setResumeId(Integer resumeId) {
		this.resumeId = resumeId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getJsonResume() {
		return jsonResume;
	}

	public void setJsonResume(String jsonResume) {
		this.jsonResume = jsonResume;
	}

	public String getHtmlResume() {
		return htmlResume;
	}

	public void setHtmlResume(String htmlResume) {
		this.htmlResume = htmlResume;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getPdfFileName() {
		return pdfFileName;
	}

	public void setPdfFileName(String pdfFileName) {
		this.pdfFileName = pdfFileName;
	}

	public String getHtmlFileName() {
		return htmlFileName;
	}

	public void setHtmlFileName(String htmlFileName) {
		this.htmlFileName = htmlFileName;
	}

	public ResumeEntity() {
	}
}
