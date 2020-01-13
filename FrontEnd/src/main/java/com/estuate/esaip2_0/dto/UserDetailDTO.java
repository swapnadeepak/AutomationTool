package com.estuate.esaip2_0.dto;

import java.util.Date;
import java.util.List;

/**
 * <ul>
 * <li>Title: UserDetailDTO</li>
 * <li>Description:To map the UserDetail fields from the view</li>
 * <li>Created by: Radhika Gopalraj</li>
 * </ul>
 */
public class UserDetailDTO {
	/**
	 * The id
	 */
	private int id;

	/**
	 * The firstName
	 */
	private String firstName;
	/**
	 * The lastName
	 */
	private String lastName;
	/**
	 * The userName
	 */
	private String userName;
	/**
	 * The emailId
	 */
	private String emailId;
	/**
	 * The password
	 */
	private String password;
	/**
	 * The status
	 */
	private String status;
	/**
	 * The type
	 */
	private int userType;

	/**
	 * The organization
	 */
	private String organization;
	/**
	 * The project
	 */
	private List<String> project;
	/**
	 * The availableGroup
	 */
	private List<String> availableGroup;
	/**
	 * The assignedGroup
	 */
	private List<String> assignedGroup;
	/**
	 * The createdDate
	 */
	private Date createdDate;
	/**
	 * The createdBy
	 */
	private String createdBy;

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
	public int getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(int userType) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserDetailDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", userName="
				+ userName + ", emailId=" + emailId + ", password=" + password + ", status=" + status + ", userType="
				+ userType + ", organization=" + organization + ", project=" + project + ", availableGroup="
				+ availableGroup + ", assignedGroup=" + assignedGroup + ", createdDate=" + createdDate + ", createdBy="
				+ createdBy + "]";
	}
}
