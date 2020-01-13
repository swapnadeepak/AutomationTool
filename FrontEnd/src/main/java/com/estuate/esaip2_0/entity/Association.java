package com.estuate.esaip2_0.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

/**
 * <ul>
 * <li>Title: Association</li>
 * <li>Description: The Association entity</li>
 * <li>Created By: Radhika GopalRaj</li>
 * </ul>
 */
@Entity	
@Component
@Table(name = "est_association")
public class Association extends SuperEntity {
	/**
	 * The compositeId
	 */
	@EmbeddedId
	private AssociationEmbeddedId compositeId;

	/**
	 * The Association constructor
	 */
	public Association() {
		super();
	}

	/**
	 * The Association constructor with AssociationEmbeddedId as parameter
	 * 
	 * @param compositeId
	 */
	public Association(AssociationEmbeddedId compositeId) {
		super();
		this.compositeId = compositeId;
	}

	/**
	 * The Association constructor with parameters
	 * 
	 * @param associationType
	 * @param associationID
	 * @param mainID
	 */
	public Association(String associationType, int associationID, int mainID) {
		compositeId = new AssociationEmbeddedId(associationType, associationID, mainID);
	}

	/**
	 * @return the compositeId
	 */
	public AssociationEmbeddedId getCompositeId() {
		return compositeId;
	}

	/**
	 * @param compositeId
	 *            the compositeId to set
	 */
	public void setCompositeId(AssociationEmbeddedId compositeId) {
		this.compositeId = compositeId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Association [compositeId=" + compositeId + "]";
	}

}
