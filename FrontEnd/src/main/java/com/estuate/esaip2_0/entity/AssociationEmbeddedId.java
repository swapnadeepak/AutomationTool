package com.estuate.esaip2_0.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * <ul>
 * <li>Title: AssociationEmbeddedId</li>
 * <li>Description: The AssociationEmbeddedId entity,used as a composite Id
 * class for Association entity.</li>
 * <li>Created By: Radhika GopalRaj</li>
 * </ul>
 */
@Embeddable
public class AssociationEmbeddedId extends SuperEntity implements Serializable {

	/**
	 * The serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The associationType
	 */
	@Column(name = "AssociationType")
	private String associationType;

	/**
	 * The associationID
	 */
	@Column(name = "AssociationID")
	private int associationID;

	/**
	 * The mainID
	 */
	@Column(name = "MainID")
	private int mainID;

	/**
	 * The default constructor
	 */
	public AssociationEmbeddedId() {
	}

	/**
	 * The constructor with parameters
	 * 
	 * @param associationType
	 * @param associationID
	 * @param mainID
	 */
	public AssociationEmbeddedId(String associationType, int associationID, int mainID) {
		super();
		this.associationType = associationType;
		this.associationID = associationID;
		this.mainID = mainID;
	}

	/**
	 * @return the associationType
	 */
	public String getAssociationType() {
		return associationType;
	}

	/**
	 * @param associationType
	 *            the associationType to set
	 */
	public void setAssociationType(String associationType) {
		this.associationType = associationType;
	}

	/**
	 * @return the associationID
	 */
	public int getAssociationID() {
		return associationID;
	}

	/**
	 * @param associationID
	 *            the associationID to set
	 */
	public void setAssociationID(int associationID) {
		this.associationID = associationID;
	}

	/**
	 * @return the mainID
	 */
	public int getMainID() {
		return mainID;
	}

	/**
	 * @param mainID
	 *            the mainID to set
	 */
	public void setMainID(int mainID) {
		this.mainID = mainID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AssociationEmbeddedId [associationType=" + associationType + ", associationID=" + associationID
				+ ", mainID=" + mainID + "]";
	}

	@Override
	public int hashCode() {
		int hash = 4;
		hash = 54 * hash + (this.associationType != null ? this.associationType.hashCode() : 0);
		hash = 54 * hash + this.associationID;
		hash = 54 * hash + this.mainID;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		// If the object is compared with itself then return true
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof AssociationEmbeddedId)) {
			return false;
		}
		// type cast obj to AssociationEmbeddedId so that we can compare data members
		AssociationEmbeddedId associationEmbeddedId = (AssociationEmbeddedId) obj;
		if ((this.associationType == null) ? (associationEmbeddedId.associationType != null)
				: !this.associationType.equals(associationEmbeddedId.associationType)) {
			return false;
		}
		if (this.associationID != associationEmbeddedId.associationID) {
			return false;
		}
		if (this.mainID != associationEmbeddedId.mainID) {
			return false;
		}

		return true;
	}

}
