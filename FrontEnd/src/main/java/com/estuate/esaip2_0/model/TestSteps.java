package com.estuate.esaip2_0.model;

import java.io.Serializable;

/**
 * <ul>
 * <li>Title: TestSteps</li>
 * <li>Description:</li>
 * <li>Created by: Nemmar Rajath Bhat</li>
 * </ul>
 */
public class TestSteps implements Serializable {
	/**
	 * The serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The tcId
	 */
	private String tcId;
	/**
	 * The businessRequirement
	 */
	private String businessRequirement;
	/**
	 * The module
	 */
	private String module;
	/**
	 * The testcase
	 */
	private String testcase;
	/**
	 * The slNo
	 */
	private String slNo;
	/**
	 * The keyword
	 */
	private String keyword;
	/**
	 * The object
	 */
	private String object;
	/**
	 * The testObjectData
	 */
	private String testObjectData;
	/**
	 * The result
	 */
	private String result;
	/**
	 * The failureReason
	 */
	private String failureReason;
	/**
	 * The screenShot
	 */
	private String screenShot;
	/**
	 * The classfile
	 */
	private String classfile;
	/**
	 * The description
	 */
	private String description;

	/**
	 * @return the tcId
	 */
	public String getTcId() {
		return tcId;
	}

	/**
	 * @param tcId
	 *            the tcId to set
	 */
	public void setTcId(String tcId) {
		this.tcId = tcId;
	}

	/**
	 * @return the businessRequirement
	 */
	public String getBusinessRequirement() {
		return businessRequirement;
	}

	/**
	 * @param businessRequirement
	 *            the businessRequirement to set
	 */
	public void setBusinessRequirement(String businessRequirement) {
		this.businessRequirement = businessRequirement;
	}

	/**
	 * @return the module
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @param module
	 *            the module to set
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * @return the testcase
	 */
	public String getTestcase() {
		return testcase;
	}

	/**
	 * @param testcase
	 *            the testcase to set
	 */
	public void setTestcase(String testcase) {
		this.testcase = testcase;
	}

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
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword
	 *            the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the object
	 */
	public String getObject() {
		return object;
	}

	/**
	 * @param object
	 *            the object to set
	 */
	public void setObject(String object) {
		this.object = object;
	}

	/**
	 * @return the testObjectData
	 */
	public String getTestObjectData() {
		return testObjectData;
	}

	/**
	 * @param testObjectData
	 *            the testObjectData to set
	 */
	public void setTestObjectData(String testObjectData) {
		this.testObjectData = testObjectData;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the failureReason
	 */
	public String getFailureReason() {
		return failureReason;
	}

	/**
	 * @param failureReason
	 *            the failureReason to set
	 */
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	/**
	 * @return the screenShot
	 */
	public String getScreenShot() {
		return screenShot;
	}

	/**
	 * @param screenShot
	 *            the screenShot to set
	 */
	public void setScreenShot(String screenShot) {
		this.screenShot = screenShot;
	}

	/**
	 * @return the classfile
	 */
	public String getClassfile() {
		return classfile;
	}

	/**
	 * @param classfile
	 *            the classfile to set
	 */
	public void setClassfile(String classfile) {
		this.classfile = classfile;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
