package com.estuate.esaip2_0.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

/**
 * <ul>
 * <li>Title: Project</li>
 * <li>Description: The Project entity</li>
 * <li>Created By: Radhika GopalRaj</li>
 * </ul>
 */
@Entity
@Component
@Table(name = "est_projectdetails")
public class Project extends SuperEntity implements Serializable {
	/**
	 * The serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ProjectID")
	private int id;
	/**
	 * The name
	 */
	@Column(name = "ProjectName")
	private String name;
	/**
	 * The type
	 */
	@Column(name = "ProjectType")
	private String type;
	/**
	 * The status
	 */
	@Column(name = "ProjectStatus")
	private String status;
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
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
		return "Project [id=" + id + ", name=" + name + ", type=" + type + ", status=" + status + ", createdDate="
				+ createdDate + ", createdBy=" + createdBy + ", modifiedDate=" + modifiedDate + ", modifiedBy="
				+ modifiedBy + "]";
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 55 * hash + (this.name != null ? this.name.hashCode() : 0);
		hash = 55 * hash + (this.type != null ? this.type.hashCode() : 0);
		hash = 55 * hash + (this.status != null ? this.status.hashCode() : 0);
		hash = 55 * hash + this.id;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		// If the object is compared with itself then return true
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Project)) {
			return false;
		}
		// typecast obj to Project so that we can compare data members
		Project project = (Project) obj;
		if ((this.name == null) ? (project.name != null) : !this.name.equals(project.name)) {
			return false;
		}
		if (this.id != project.id) {
			return false;
		}
		if ((this.type == null) ? (project.type != null) : !this.type.equals(project.type)) {
			return false;
		}
		if ((this.status == null) ? (project.status != null) : !this.status.equals(project.status)) {
			return false;
		}
		return true;
	}
}
