package com.estuate.esaip2_0.model;

import java.io.Serializable;

/**
 * <ul>
 * <li>Title: AllObjects</li>
 * <li>Description:</li>
 * <li>Created by: Nemmar Rajath Bhat</li>
 * </ul>
 */
public class AllObjects implements Serializable {
	/**
	 * The serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The slNo
	 */
	private String slNo;
	/**
	 * The screenName
	 */
	private String screenName;
	/**
	 * The objectName
	 */
	private String objectName;
	/**
	 * The type
	 */
	private String type;
	/**
	 * The values
	 */
	private String values;
	/**
	 * The comments
	 */
	private String comments;

	/**
	 * @return the slNo
	 */
	public String getSlNo() {
		return slNo;
	}

	/**
	 * @param slNo
	 *            the slNo to set
	 */
	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}

	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * @param screenName
	 *            the screenName to set
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	/**
	 * @return the objectName
	 */
	public String getObjectName() {
		return objectName;
	}

	/**
	 * @param objectName
	 *            the objectName to set
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
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
	 * @return the values
	 */
	public String getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(String values) {
		this.values = values;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
}
