package com.estuate.esaip2_0.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

/**
 * <ul>
 * <li>Title: UserDetail</li>
 * <li>Description: The UserDetail entity</li>
 * <li>Created By: Radhika GopalRaj</li>
 * </ul>
 */
@Entity
@Component
@Table(name = "est_userdetails")
public class UserDetail extends SuperEntity {
	/**
	 * The id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "UserID")
	private int id;

	/**
	 * The firstName
	 */
	@Column(name = "FirstName")
	private String firstName;
	/**
	 * The lastName
	 */
	@Column(name = "LastName")
	private String lastName;
	/**
	 * The userName
	 */
	@Column(name = "Username")
	private String userName;
	/**
	 * The emailId
	 */
	@Column(name = "EmailID")
	private String emailId;
	/**
	 * The password
	 */
	@Column(name = "UserPassword")
	private String password;
	/**
	 * The status
	 */
	@Column(name = "UserStatus")
	private String status;
	/**
	 * The type
	 */
	@OneToOne
	@JoinColumn(name = "TypeID")
	private UserType userType;

	/**
	 * The organization
	 */
	@Transient
	private String organization;
	/**
	 * The project
	 */
	@Transient
	private List<String> project;
	/**
	 * The availableGroup
	 */
	@Transient
	private List<String> availableGroup;
	/**
	 * The assignedGroup
	 */
	@Transient
	private List<String> assignedGroup;
	/**
	 * The createdDate
	 */
	@Column(name = "CreatedDate")
	private Date createdDate;
	/**
	 * The createdBy
	 */
	@Column(name = "CreatedBy")
	private String createdBy;
	/**
	 * The modifiedDate
	 */
	@Column(name = "ModifiedDate")
	private Date modifiedDate;
	/**
	 * The modifiedBy
	 */
	@Column(name = "ModifiedBy")
	private String modifiedBy;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId
	 *            the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the userType
	 */
	public UserType getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * @param organization
	 *            the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * @return the project
	 */
	public List<String> getProject() {
		return project;
	}

	/**
	 * @param project
	 *            the project to set
	 */
	public void setProject(List<String> project) {
		this.project = project;
	}

	/**
	 * @return the availableGroup
	 */
	public List<String> getAvailableGroup() {
		return availableGroup;
	}

	/**
	 * @param availableGroup
	 *            the availableGroup to set
	 */
	public void setAvailableGroup(List<String> availableGroup) {
		this.availableGroup = availableGroup;
	}

	/**
	 * @return the assignedGroup
	 */
	public List<String> getAssignedGroup() {
		return assignedGroup;
	}

	/**
	 * @param assignedGroup
	 *            the assignedGroup to set
	 */
	public void setAssignedGroup(List<String> assignedGroup) {
		this.assignedGroup = assignedGroup;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate
	 *            the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy
	 *            the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName
				+ ", emailId=" + emailId + ", password=" + password + ", status=" + status + ", userType=" + userType
				+ ", organization=" + organization + ", project=" + project + ", availableGroup=" + availableGroup
				+ ", assignedGroup=" + assignedGroup + ", createdDate=" + createdDate + ", createdBy=" + createdBy
				+ ", modifiedDate=" + modifiedDate + ", modifiedBy=" + modifiedBy + "]";
	}
	
	
}
