package com.estuate.esaip2_0.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

/**
 * <ul>
 * <li>Title:</li>
 * <li>Description: The Group entity</li>
 * <li>Created By: Radhika GopalRaj</li>
 * </ul>
 */
@Entity
@Component
@Table(name = "est_groups")
public class Group extends SuperEntity {
	/**
	 * The groupId
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "GroupId")
	private int id;
	/**
	 * The groupName
	 */
	@Column(name = "GroupName")
	private String name;
	/**
	 * The groupType
	 */
	@Column(name = "GroupType")
	private String type;
	/**
	 * The groupDisplayName
	 */
	@Column(name = "GroupDisplayName")
	private String displayName;
	/**
	 * The assignedRoles
	 */
	@Transient
	private List<String> assignedRoles;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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

	/**
	 * @return the assignedRoles
	 */
	public List<String> getAssignedRoles() {
		return assignedRoles;
	}

	/**
	 * @param assignedRoles
	 *            the assignedRoles to set
	 */
	public void setAssignedRoles(List<String> assignedRoles) {
		this.assignedRoles = assignedRoles;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", type=" + type + ", displayName=" + displayName
				+ ", assignedRoles=" + assignedRoles + "]";
	}

	@Override
	public boolean equals(Object obj) {
		// If the object is compared with itself then return true
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Group)) {
			return false;
		}
		// type cast obj to Group so that we can compare data members
		Group group = (Group) obj;
		if ((this.name == null) ? (group.name != null) : !this.name.equals(group.name)) {
			return false;
		}
		if (this.id != group.id) {
			return false;
		}
		if ((this.type == null) ? (group.type != null) : !this.type.equals(group.type)) {
			return false;
		}
		if ((this.displayName == null) ? (group.displayName != null) : !this.displayName.equals(group.displayName)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
		hash = 53 * hash + this.id;
		hash = 53 * hash + (this.type != null ? this.type.hashCode() : 0);
		hash = 53 * hash + (this.displayName != null ? this.displayName.hashCode() : 0);
		hash = 53 * hash + (this.assignedRoles != null ? this.assignedRoles.size() : 0);
		return hash;
	}

}
