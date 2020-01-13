package com.estuate.esaip2_0.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

/**
 * <ul>
 * <li>Title: Role</li>
 * <li>Description: The Role entity</li>
 * <li>Created By: Radhika GopalRaj</li>
 * </ul>
 */
@Entity
@Component
@Table(name = "est_roles")
public class Role extends SuperEntity implements Serializable {
	/**
	 * The serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RoleID")
	private int id;
	/**
	 * The name
	 */
	@Column(name = "RoleName")
	private String name;
	/**
	 * The displayName
	 */
	@Column(name = "DisplayRoleName")
	private String displayName;

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
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", displayName=" + displayName + "]";
	}

	@Override
	public boolean equals(Object obj) {
		// If the object is compared with itself then return true
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Role)) {
			return false;
		}
		// type cast obj to Role so that we can compare data members
		Role role = (Role) obj;
		if ((this.name == null) ? (role.name != null) : !this.name.equals(role.name)) {
			return false;
		}
		if (this.id != role.id) {
			return false;
		}
		if ((this.displayName == null) ? (role.displayName != null) : !this.displayName.equals(role.displayName)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
		hash = 53 * hash + this.id;
		hash = 53 * hash + (this.displayName != null ? this.displayName.hashCode() : 0);
		return hash;
	}

}
