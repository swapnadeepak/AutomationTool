package com.estuate.esaip2_0.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

/**
 * <ul>
 * <li>Title:</li>
 * <li>Description: The AttemptHistory entity</li>
 * <li>Created By: Radhika GopalRaj</li>
 * </ul>
 */
@Entity
@Component
@Table(name = "est_attempthistory")
public class AttemptHistory extends SuperEntity {
	/**
	 * The id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SerialID")
	private int id;
	/**
	 * The user
	 */
	@OneToOne
	@JoinColumn(name = "UserID")
	private UserDetail user;
	/**
	 * The organization
	 */
	@OneToOne
	@JoinColumn(name = "OrgID")
	private Organization organization;
	/**
	 * The sessionId
	 */
	private String sessionId;
	/**
	 * The clientDate
	 */
	private Date clientDate;
	/**
	 * The serverDate
	 */
	private Date serverDate;
	/**
	 * The attemptCount
	 */
	private int attemptCount;

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
	 * @return the user
	 */
	public UserDetail getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(UserDetail user) {
		this.user = user;
	}

	/**
	 * @return the organization
	 */
	public Organization getOrganization() {
		return organization;
	}

	/**
	 * @param organization
	 *            the organization to set
	 */
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the clientDate
	 */
	public Date getClientDate() {
		return clientDate;
	}

	/**
	 * @param clientDate
	 *            the clientDate to set
	 */
	public void setClientDate(Date clientDate) {
		this.clientDate = clientDate;
	}

	/**
	 * @return the serverDate
	 */
	public Date getServerDate() {
		return serverDate;
	}

	/**
	 * @param serverDate
	 *            the serverDate to set
	 */
	public void setServerDate(Date serverDate) {
		this.serverDate = serverDate;
	}

	/**
	 * @return the attemptCount
	 */
	public int getAttemptCount() {
		return attemptCount;
	}

	/**
	 * @param attemptCount
	 *            the attemptCount to set
	 */
	public void setAttemptCount(int attemptCount) {
		this.attemptCount = attemptCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AttemptHistory [id=" + id + ", user=" + user + ", organization=" + organization + ", sessionId="
				+ sessionId + ", clientDate=" + clientDate + ", serverDate=" + serverDate + ", attemptCount="
				+ attemptCount + "]";
	}

}
