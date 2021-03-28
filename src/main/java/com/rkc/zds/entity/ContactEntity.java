package com.rkc.zds.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PcmContacts generated by hbm2java
 */
@Entity
@Table(name = "PCM_CONTACTS")
public class ContactEntity implements java.io.Serializable {
	
    private static final long serialVersionUID = -6809049173391335091L;
	
	@Id
	@Column(name="CONTACT_ID", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private Integer userId;
	private String firstName;
	private String lastName;
	private String title;
	private String company;
	private String presenceImageUrl;

	public ContactEntity() {
	}

	public ContactEntity(int id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public ContactEntity(int id, String firstName, String lastName, String title, String company) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.company = company;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "USER_ID")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "FIRSTNAME", nullable = false, length = 100)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "LASTNAME", nullable = false, length = 100)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "TITLE", length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "COMPANY", length = 100)
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	@Column(name = "PRESENCE_IMAGE_URL", length = 100)
	public String getPresenceImageUrl() {
		return this.presenceImageUrl;
	}

	public void setPresenceImageUrl(String presenceImageUrl) {
		this.presenceImageUrl = presenceImageUrl;
	}
	
	@Override
	public int hashCode() {
	    int hash = 3;
	    hash = 53 * hash + (this.id);
	    return hash;
	}

	@Override
	public boolean equals(Object other) {
	    boolean result;
	    if((other == null) || (getClass() != other.getClass())){
	        result = false;
	    } // end if
	    else{
	        ContactEntity otherContact = (ContactEntity)other;
	        result = (id == (otherContact.id));
	    } // end else

	    return result;
	}

}