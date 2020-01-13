package com.estuate.esaip2_0.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <ul>
 * <li>Title: UserType</li>
 * <li>Description: The UserType entity</li>
 * <li>Created By: Radhika GopalRaj</li>
 * </ul>
 */
@Entity
@Table(name = "est_usertype")
public class UserType extends SuperEntity {
	/**
	 * The id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TypeID")
	private int id;

	/**
	 * The UserTypeName
	 */
	@Column(name = "UserTypeName")
	private String name;
	/**
	 * The typeDisplayName
	 */
	@Column(name = "TypeDisplayName")
	private String displayName;
	/**
	 * The userDetail
	 */
	@Transient
	@OneToOne(mappedBy = "userType")
	private UserDetail userDetail;

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
	 * @return the userTypeName
	 */
	public String getUserTypeName() {
		return name;
	}

	/**
	 * @param userTypeName
	 *            the userTypeName to set
	 */
	public void setUserTypeName(String userTypeName) {
		this.name = userTypeName;
	}
	
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserType [id=" + id + ", name=" + name + ", displayName=" + displayName + ", userDetail=" + userDetail
				+ "]";
	}
}
