package com.estuate.esaip2_0.model;

import java.io.Serializable;

/**
 * <ul>
 * <li>Title: RunPlan</li>
 * <li>Description: This class is for run plan tab of excel.</li>
 * <li>Created by: Nemmar Rajath Bhat</li>
 * <li>Modified By: Radhika GopalRaj</li>
 * </ul>
 */
public class RunPlan implements Serializable {
	/**
	 * The serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The index
	 */
	private int index;
	/**
	 * The id
	 */
	private String id;
	/**
	 * The BR
	 */
	private String BR;
	/**
	 * The module
	 */
	private String module;
	/**
	 * The testCase
	 */
	private String testCase;
	/**
	 * The testCaseId
	 */
	private String testCaseId;
	/**
	 * The criticality
	 */
	private String criticality;
	/**
	 * The result
	 */
	private String result;
	/**
	 * The timeTaken
	 */
	private String timeTaken;
	/**
	 * The repeatablity
	 */
	private String repeatablity;
	/**
	 * The fileLocation
	 */
	private String fileLocation;
	/**
	 * The fileName
	 */
	private String fileName;

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the bR
	 */
	public String getBR() {
		return BR;
	}

	/**
	 * @param bR
	 *            the bR to set
	 */
	public void setBR(String bR) {
		BR = bR;
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
	 * @return the testCase
	 */
	public String getTestCase() {
		return testCase;
	}

	/**
	 * @param testCase
	 *            the testCase to set
	 */
	public void setTestCase(String testCase) {
		this.testCase = testCase;
	}

	/**
	 * @return the testCaseId
	 */
	public String getTestCaseId() {
		return testCaseId;
	}

	/**
	 * @param testCaseId
	 *            the testCaseId to set
	 */
	public void setTestCaseId(String testCaseId) {
		this.testCaseId = testCaseId;
	}

	/**
	 * @return the criticality
	 */
	public String getCriticality() {
		return criticality;
	}

	/**
	 * @param criticality
	 *            the criticality to set
	 */
	public void setCriticality(String criticality) {
		this.criticality = criticality;
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
	 * @return the timeTaken
	 */
	public String getTimeTaken() {
		return timeTaken;
	}

	/**
	 * @param timeTaken
	 *            the timeTaken to set
	 */
	public void setTimeTaken(String timeTaken) {
		this.timeTaken = timeTaken;
	}

	/**
	 * @return the repeatablity
	 */
	public String getRepeatablity() {
		return repeatablity;
	}

	/**
	 * @param repeatablity
	 *            the repeatablity to set
	 */
	public void setRepeatablity(String repeatablity) {
		this.repeatablity = repeatablity;
	}

	/**
	 * @return the fileLocation
	 */
	public String getFileLocation() {
		return fileLocation;
	}

	/**
	 * @param fileLocation
	 *            the fileLocation to set
	 */
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RunPlan [index=" + index + ", id=" + id + ", BR=" + BR + ", module=" + module + ", testCase=" + testCase
				+ ", testCaseId=" + testCaseId + ", criticality=" + criticality + ", result=" + result + ", timeTaken="
				+ timeTaken + ", repeatability=" + repeatablity + ", fileLocation=" + fileLocation + ", fileName="
				+ fileName + "]";
	}

}
